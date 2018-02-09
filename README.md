# ctripHomeScaleView
Android仿携程首页按压缩放效果view

# Screenshots
![](https://github.com/kuangxiaoguo0123/ctripHomeScaleView/raw/master/screenshots/ctrip.gif)

# 实现动画

````
/**
     * Init scale animation
     */
    private void initAnimation() {
        ObjectAnimator beginXAnimation = ObjectAnimator.ofFloat(this, "scaleX", ONE_SCALE, SMALL_SCALE).setDuration(DURATION);
        beginXAnimation.setInterpolator(new LinearInterpolator());
        ObjectAnimator beginYAnimation = ObjectAnimator.ofFloat(this, "scaleY", ONE_SCALE, SMALL_SCALE).setDuration(DURATION);
        beginYAnimation.setInterpolator(new LinearInterpolator());
        ObjectAnimator backXAnimation = ObjectAnimator.ofFloat(this, "scaleX", SMALL_SCALE, ONE_SCALE).setDuration(DURATION);
        backXAnimation.setInterpolator(new LinearInterpolator());
        ObjectAnimator backYAnimation = ObjectAnimator.ofFloat(this, "scaleY", SMALL_SCALE, ONE_SCALE).setDuration(DURATION);
        backYAnimation.setInterpolator(new LinearInterpolator());
        beginAnimatorSet = new AnimatorSet();
        beginAnimatorSet.play(beginXAnimation).with(beginYAnimation);
        backAnimatorSet = new AnimatorSet();
        backAnimatorSet.play(backXAnimation).with(backYAnimation);
    }
````
# ImageView的onTouch事件处理

````
@Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /**
                 * 判断是不是快速点击
                 */
                if (isFastClick()) {
                    return true;
                }
                /**
                 * 请求父类不要拦截我的事件,意思是让我来处理接下来的滑动或别的事件
                 */
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = (int) event.getX();
                downY = (int) event.getY();
                hasDoneAnimation = false;
                post(new Runnable() {
                    @Override
                    public void run() {
                        beginAnimatorSet.start();
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int moveY = (int) event.getY();
                int moveDistanceX = Math.abs(moveX) - downX;
                int moveDistanceY = Math.abs(moveY) - downY;
                /**
                 * 这里是判断是向左还是向右滑动,然后用view的宽度计算出一个距离compareWidth,当滑动距离超出compareWidth时,需要执行返回动画.
                 */
                int compareWidth = moveDistanceX > 0 ? getWidth() - downX : downX;
                /**
                 * 第一个条件:判断向上或向下滑动距离大于滑动最小距离
                 * 第二个条件:判断向左或向右的滑动距离是否超出(compareWidth-最小距离)
                 * 第三个条件:判断有没有执行过返回动画并在执行过一次后置为true.
                 */
                if ((Math.abs(moveDistanceY) > dip2px(MIN_MOVE_DPI) || Math.abs(moveDistanceX) >= compareWidth - dip2px(MIN_MOVE_DPI)) && !hasDoneAnimation) {
                    /**
                     * 一 只要满足上述条件,就代表用户不是点击view,而是执行了滑动操作,这个时候我们就需要父类以及我们的最上层的控件来
                     * 拦截我们的事件,让最外层控件处理接下来的事件,比如scrollview的滑动.
                     * 二 因为我们执行了滑动操作,所以要执行view的返回动画
                     */
                    getParent().requestDisallowInterceptTouchEvent(false);
                    hasDoneAnimation = true;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            backAnimatorSet.start();
                        }
                    });
                }
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 这里如果我们是单纯的点击事件就会执行
                 */
                if (!hasDoneAnimation) {
                    hasDoneAnimation = true;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            backAnimatorSet.start();
                        }
                    });
                    post(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 接口回调点击事件
                             */
                            if (listener != null) {
                                listener.onClick(CTripHomeScaleView.this);
                            }
                        }
                    });
                }
                break;
        }
        return true;
    }
````
# Sample source code
[https://github.com/kuangxiaoguo0123/ctripHomeScaleView](https://github.com/kuangxiaoguo0123/ctripHomeScaleView)

# More information
[http://blog.csdn.net/kuangxiaoguo0123/article/details/52373659](http://blog.csdn.net/kuangxiaoguo0123/article/details/52373659)
