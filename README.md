[![](https://jitpack.io/v/WZLYAndroid/WzlyLib-base.svg)](https://jitpack.io/#WZLYAndroid/WzlyLib-base)

# WzlyLib-base

程序入口初始化 ： 

	BaseLib.init(Application, int, int...);
	//Logger
	FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
	        .tag(AppConstant.LOG_TAG)
	        .build();
	Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
	    @Override
	    public boolean isLoggable(int priority, @Nullable String tag) {
	        //return true;
	        return AppUtil.isAppDebug(getAppContext());
	    }
	});




发布流程 ：
