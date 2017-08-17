package view.recicle.table.kirill.sorokin.ru.tablerecicleview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * Created by Kirill on 28.04.2017.
 */
public class TableLayoutManager extends RecyclerView.LayoutManager {

    //TODO размеры нужно подгружать для каждого типа view
    private static final int VIEW_HEIGHT = 180;
    private static final int VIEW_WIDTH = 210;

    private HashMap<Cell, View> viewCache = new HashMap<>();
    private TableAdapter tableAdapter;

    public TableLayoutManager(TableAdapter tableAdapter) {
        this.tableAdapter = tableAdapter;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        fill(recycler);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = scrollVerticallyInternal(dy);
        offsetChildrenVertical(-delta);
        fill(recycler);
        return delta;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = scrollHorizontallyInternal(dx);
        offsetChildrenHorizontal(-delta);
        fill(recycler);
        return delta;
    }

    private void fill(RecyclerView.Recycler recycler) {
        viewCache.clear();
        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            View view = getChildAt(i);
            int pos = getPosition(view);
            viewCache.put(tableAdapter.getCellAtPosition(pos), view);
        }
        View anchor = getAnchorView();
        for (Cell cell : viewCache.keySet()) {
            detachView(viewCache.get(cell));
        }
        fillInternal(anchor, recycler);
        for (Cell cell : viewCache.keySet()) {
            recycler.recycleView(viewCache.get(cell));
        }
    }

    private View getAnchorView() {
        int childCount = getChildCount();
        Rect mainRect = new Rect(0, 0, getWidth(), getHeight());
        View anchorView = null;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int top = getDecoratedTop(view);
            int bottom = getDecoratedBottom(view);
            int left = getDecoratedLeft(view);
            int right = getDecoratedRight(view);
            Rect viewRect = new Rect(left, top, right, bottom);
            boolean intersect = viewRect.intersect(mainRect);
            if (intersect) {
                anchorView = view;
                break;
            }
        }
        return anchorView;
    }

    //метод возвращает левую верхнюю и правую нижнюю видимые ячейки
    private Cell[] calculateRestrictions(View anchor) {
        final Cell[] restrictions = new Cell[2];
        final int height = getHeight();
        final int width = getWidth();

        int left = getDecoratedLeft(anchor);
        int top = getDecoratedTop(anchor);
        int right = getDecoratedRight(anchor);
        int bottom = getDecoratedBottom(anchor);
        Cell anchorCell = tableAdapter.getCellAtPosition(getPosition(anchor));
        int countColumn = tableAdapter.getCountColumn();
        int countRow = tableAdapter.getCountRow();

        //ищем левый верхний угол
        restrictions[0] = new Cell(anchorCell.getX(), anchorCell.getY());
        while (left > 0 && restrictions[0].getX() >= 1) {
            left -= VIEW_WIDTH;
            restrictions[0].setX(restrictions[0].getX() - 1);
        }
        while (top > 0 && restrictions[0].getY() >= 1) {
            top -= VIEW_HEIGHT;
            restrictions[0].setY(restrictions[0].getY() - 1);
        }
        //ищем правый нижний угол
        restrictions[1] = new Cell(anchorCell.getX(), anchorCell.getY());
        while (right < width && restrictions[1].getX() <= countColumn) {
            right += VIEW_WIDTH;
            restrictions[1].setX(restrictions[1].getX() + 1);
        }
        while (bottom < height && restrictions[1].getY() <= countRow) {
            bottom += VIEW_HEIGHT;
            restrictions[1].setY(restrictions[1].getY() + 1);
        }
        return restrictions;
    }

    private void fillInternal(View anchor, RecyclerView.Recycler recycler) {
        if (getItemCount() == 0) {
            return;
        }
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(VIEW_WIDTH, View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(VIEW_HEIGHT, View.MeasureSpec.EXACTLY);

        int leftOffset = 0;
        int topOffset = 0;
        Cell[] restrictions;
        if (anchor != null) {
            restrictions = calculateRestrictions(anchor);
            leftOffset = getDecoratedLeft(anchor);
            topOffset = getDecoratedTop(anchor);

            Cell anchorCell = tableAdapter.getCellAtPosition(getPosition(anchor));
            leftOffset -= anchorCell.getX() * VIEW_WIDTH;
            topOffset -= anchorCell.getY() * VIEW_HEIGHT;
        } else {
            restrictions = calculateRestrictions(recycler.getViewForPosition(0));
        }
        for (int y = restrictions[0].getY(); y <= restrictions[1].getY(); y++) {
            for (int x = restrictions[0].getX(); x <= restrictions[1].getX(); x++) {
                Cell cell = new Cell(x, y);
                View view = viewCache.get(cell);
                if (view == null) {
                    int position = tableAdapter.getPosition(cell);
                    view = recycler.getViewForPosition(position);
                    addView(view);
                    measureChildWithDecorationsAndMargin(view, widthSpec, heightSpec);

                    int left = leftOffset + (x * VIEW_WIDTH);
                    int top = topOffset + (y * VIEW_HEIGHT);
                    layoutDecorated(view,
                            left,               //left
                            top,                //top
                            left + VIEW_WIDTH,  //right
                            top + VIEW_HEIGHT); //bottom
                } else {
                    attachView(view);
                    viewCache.remove(cell);
                }
            }
        }
    }

    private void measureChildWithDecorationsAndMargin(View child, int widthSpec, int heightSpec) {
        Rect decorRect = new Rect();
        calculateItemDecorationsForChild(child, decorRect);
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
        widthSpec = updateSpecWithExtra(widthSpec,
                lp.leftMargin + decorRect.left,
                lp.rightMargin + decorRect.right);
        heightSpec = updateSpecWithExtra(heightSpec,
                lp.topMargin + decorRect.top,
                lp.bottomMargin + decorRect.bottom);
        child.measure(widthSpec, heightSpec);
    }

    private int updateSpecWithExtra(int spec, int startInset, int endInset) {
        if (startInset == 0 && endInset == 0) {
            return spec;
        }
        final int mode = View.MeasureSpec.getMode(spec);
        if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            return View.MeasureSpec.makeMeasureSpec(
                    View.MeasureSpec.getSize(spec) - startInset - endInset, mode);
        }
        return spec;
    }

    private int scrollVerticallyInternal(int dy) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }

        final View topView = getChildAt(0);
        final View bottomView = getChildAt(childCount - 1);

        //Случай, когда все вьюшки поместились на экране
        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
        if (viewSpan <= getHeight()) {
            return 0;
        }

        int delta = 0;
        if (dy < 0) {//если контент уезжает вниз
            View firstView = getChildAt(0);
            Cell cell = tableAdapter.getCellAtPosition(getPosition(firstView));
            if (cell.getY() > 0) {
                delta = dy;
            } else {
                int viewTop = getDecoratedTop(firstView);
                delta = Math.max(viewTop, dy);
            }
        } else if (dy > 0) {//если контент уезжает вверх
            View lastView = getChildAt(childCount - 1);
            int count = getItemCount();
            Cell lastCell = tableAdapter.getCellAtPosition(count - 1);
            Cell cell = tableAdapter.getCellAtPosition(getPosition(lastView));
            if (lastCell.getY() > cell.getY()) {
                delta = dy;
            } else {
                int viewBottom = getDecoratedBottom(lastView);
                int parentBottom = getHeight();
                delta = Math.min(viewBottom - parentBottom, dy);
            }
        }
        return delta;
    }

    private int scrollHorizontallyInternal(int dx) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        final View leftView = getChildAt(0);
        final View rightView = getChildAt(childCount - 1);

        int viewSpan = getDecoratedRight(rightView) - getDecoratedLeft(leftView);
        if (viewSpan <= getWidth()) {
            return 0;
        }

        int delta = 0;
        if (dx < 0) {//если контент уезжает вправо
            View firstView = getChildAt(0);
            Cell cell = tableAdapter.getCellAtPosition(getPosition(firstView));
            if (cell.getX() > 0) {
                delta = dx;
            } else {
                int viewLeft = getDecoratedLeft(firstView);
                delta = Math.max(viewLeft, dx);
            }
        } else if (dx > 0) {//если контент уезжает влево
            View lastView = getChildAt(childCount - 1);
            int count = getItemCount();
            Cell lastCell = tableAdapter.getCellAtPosition(count - 1);
            Cell cell = tableAdapter.getCellAtPosition(getPosition(lastView));
            if (lastCell.getX() > cell.getX()) {
                delta = dx;
            } else {
                int viewRight = getDecoratedRight(lastView);
                delta = Math.min(viewRight - getWidth(), dx);
            }
        }
        return delta;
    }
}
