package com.example.vocab_master_mad_project;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VocabManager {
    private static VocabManager instance;
    private final VocabDao vocabDao;

    private VocabManager(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        vocabDao = db.vocabDao();
        seedDatabaseIfEmpty();
    }

    public static synchronized VocabManager getInstance(Context context) {
        if (instance == null) {
            instance = new VocabManager(context);
        }
        return instance;
    }

    // For backward compatibility with existing calls that might not have context yet
    // though it's better to always pass context.
    public static VocabManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("VocabManager must be initialized with context first.");
        }
        return instance;
    }

    public List<Vocab> getAllVocabs() {
        return vocabDao.getAllVocabs();
    }

    public List<String> getAllGroups() {
        return vocabDao.getAllGroups();
    }

    public List<Vocab> getRandomVocabs(String difficulty) {
        // If it's a built-in difficulty, return fixed count
        if ("Basic".equalsIgnoreCase(difficulty)) {
            return getRandomVocabs(difficulty, 5);
        } else if ("Enhanced".equalsIgnoreCase(difficulty)) {
            return getRandomVocabs(difficulty, 10);
        } else if ("Elite".equalsIgnoreCase(difficulty)) {
            return getRandomVocabs(difficulty, 15);
        } else {
            // It's a custom group, return ALL vocabs in that group
            List<Vocab> source = vocabDao.getVocabsByDifficulty(difficulty);
            List<Vocab> result = new ArrayList<>(source);
            Collections.shuffle(result);
            return result;
        }
    }

    public List<Vocab> getRandomVocabs(String difficulty, int count) {
        List<Vocab> source = vocabDao.getVocabsByDifficulty(difficulty);
        List<Vocab> shuffled = new ArrayList<>(source);
        Collections.shuffle(shuffled);
        return new ArrayList<>(shuffled.subList(0, Math.min(count, shuffled.size())));
    }

    public List<Vocab> getSortedVocabs(String difficulty) {
        return vocabDao.getVocabsByDifficulty(difficulty);
    }

    public void addVocab(Vocab vocab) {
        vocabDao.insert(vocab);
    }

    public void deleteVocab(Vocab vocab) {
        vocabDao.delete(vocab);
    }

    public void deleteGroup(String difficulty) {
        vocabDao.deleteGroupByDifficulty(difficulty);
    }

    private void seedDatabaseIfEmpty() {
        if (vocabDao.getAllVocabs().isEmpty()) {
            List<Vocab> initialVocabs = new ArrayList<>();
            // Basic Mode
            initialVocabs.add(new Vocab("Apple", "n.", "蘋果", "Basic"));
            initialVocabs.add(new Vocab("Banana", "n.", "香蕉", "Basic"));
            initialVocabs.add(new Vocab("Book", "n.", "書本", "Basic"));
            initialVocabs.add(new Vocab("Cat", "n.", "貓", "Basic"));
            initialVocabs.add(new Vocab("Dog", "n.", "狗", "Basic"));
            initialVocabs.add(new Vocab("Eat", "v.", "吃", "Basic"));
            initialVocabs.add(new Vocab("Family", "n.", "家庭", "Basic"));
            initialVocabs.add(new Vocab("Friend", "n.", "朋友", "Basic"));
            initialVocabs.add(new Vocab("Green", "adj.", "綠色的", "Basic"));
            initialVocabs.add(new Vocab("Happy", "adj.", "快樂的", "Basic"));
            initialVocabs.add(new Vocab("House", "n.", "房屋", "Basic"));
            initialVocabs.add(new Vocab("Jump", "v.", "跳躍", "Basic"));
            initialVocabs.add(new Vocab("King", "n.", "國王", "Basic"));
            initialVocabs.add(new Vocab("Learn", "v.", "學習", "Basic"));
            initialVocabs.add(new Vocab("Music", "n.", "音樂", "Basic"));
            initialVocabs.add(new Vocab("Night", "n.", "夜晚", "Basic"));
            initialVocabs.add(new Vocab("Orange", "n.", "橙", "Basic"));
            initialVocabs.add(new Vocab("Paper", "n.", "紙張", "Basic"));
            initialVocabs.add(new Vocab("Quiet", "adj.", "安靜的", "Basic"));
            initialVocabs.add(new Vocab("Run", "v.", "跑步", "Basic"));
            initialVocabs.add(new Vocab("School", "n.", "學校", "Basic"));
            initialVocabs.add(new Vocab("Teacher", "n.", "老師", "Basic"));
            initialVocabs.add(new Vocab("Under", "prep.", "在...下面", "Basic"));
            initialVocabs.add(new Vocab("Visit", "v.", "訪問", "Basic"));
            initialVocabs.add(new Vocab("Water", "n.", "水", "Basic"));
            initialVocabs.add(new Vocab("Yellow", "adj.", "黃色的", "Basic"));
            initialVocabs.add(new Vocab("Zebra", "n.", "斑馬", "Basic"));
            initialVocabs.add(new Vocab("Bread", "n.", "麵包", "Basic"));
            initialVocabs.add(new Vocab("Clean", "adj.", "乾淨的", "Basic"));
            initialVocabs.add(new Vocab("Dance", "v.", "跳舞", "Basic"));
            initialVocabs.add(new Vocab("Early", "adv.", "早地", "Basic"));
            initialVocabs.add(new Vocab("Flower", "n.", "花朵", "Basic"));
            initialVocabs.add(new Vocab("Garden", "n.", "花園", "Basic"));
            initialVocabs.add(new Vocab("Hello", "int.", "你好", "Basic"));
            initialVocabs.add(new Vocab("Ice", "n.", "冰", "Basic"));
            initialVocabs.add(new Vocab("Joke", "n.", "玩笑", "Basic"));
            initialVocabs.add(new Vocab("Kitchen", "n.", "廚房", "Basic"));
            initialVocabs.add(new Vocab("Light", "n.", "光", "Basic"));
            initialVocabs.add(new Vocab("Morning", "n.", "早上", "Basic"));
            initialVocabs.add(new Vocab("Name", "n.", "名字", "Basic"));
            initialVocabs.add(new Vocab("Open", "v.", "打開", "Basic"));
            initialVocabs.add(new Vocab("Play", "v.", "玩", "Basic"));
            initialVocabs.add(new Vocab("Rain", "n.", "雨", "Basic"));
            initialVocabs.add(new Vocab("Smile", "n.", "微笑", "Basic"));
            initialVocabs.add(new Vocab("Table", "n.", "桌子", "Basic"));
            initialVocabs.add(new Vocab("Uncle", "n.", "叔叔", "Basic"));
            initialVocabs.add(new Vocab("Voice", "n.", "聲音", "Basic"));
            initialVocabs.add(new Vocab("Window", "n.", "窗戶", "Basic"));
            initialVocabs.add(new Vocab("Young", "adj.", "年輕的", "Basic"));
            initialVocabs.add(new Vocab("Zoo", "n.", "動物園", "Basic"));

            // --- 身體與健康 (20) ---
            initialVocabs.add(new Vocab("Eye", "n.", "眼睛", "Basic"));
            initialVocabs.add(new Vocab("Ear", "n.", "耳朵", "Basic"));
            initialVocabs.add(new Vocab("Mouth", "n.", "嘴巴", "Basic"));
            initialVocabs.add(new Vocab("Nose", "n.", "鼻子", "Basic"));
            initialVocabs.add(new Vocab("Hand", "n.", "手", "Basic"));
            initialVocabs.add(new Vocab("Foot", "n.", "腳", "Basic"));
            initialVocabs.add(new Vocab("Arm", "n.", "手臂", "Basic"));
            initialVocabs.add(new Vocab("Leg", "n.", "腿", "Basic"));
            initialVocabs.add(new Vocab("Head", "n.", "頭", "Basic"));
            initialVocabs.add(new Vocab("Hair", "n.", "頭髮", "Basic"));
            initialVocabs.add(new Vocab("Face", "n.", "臉", "Basic"));
            initialVocabs.add(new Vocab("Body", "n.", "身體", "Basic"));
            initialVocabs.add(new Vocab("Tooth", "n.", "牙齒", "Basic"));
            initialVocabs.add(new Vocab("Finger", "n.", "手指", "Basic"));
            initialVocabs.add(new Vocab("Blood", "n.", "血液", "Basic"));
            initialVocabs.add(new Vocab("Heart", "n.", "心臟", "Basic"));
            initialVocabs.add(new Vocab("Back", "n.", "背部", "Basic"));
            initialVocabs.add(new Vocab("Sleep", "v.", "睡覺", "Basic"));
            initialVocabs.add(new Vocab("Wash", "v.", "清洗", "Basic"));
            initialVocabs.add(new Vocab("Sick", "adj.", "生病的", "Basic"));

// --- 衣物與飾品 (20) ---
            initialVocabs.add(new Vocab("Shirt", "n.", "襯衫", "Basic"));
            initialVocabs.add(new Vocab("Pants", "n.", "褲子", "Basic"));
            initialVocabs.add(new Vocab("Dress", "n.", "連衣裙", "Basic"));
            initialVocabs.add(new Vocab("Skirt", "n.", "裙子", "Basic"));
            initialVocabs.add(new Vocab("Hat", "n.", "帽子", "Basic"));
            initialVocabs.add(new Vocab("Coat", "n.", "大衣", "Basic"));
            initialVocabs.add(new Vocab("Shoe", "n.", "鞋子", "Basic"));
            initialVocabs.add(new Vocab("Sock", "n.", "襪子", "Basic"));
            initialVocabs.add(new Vocab("Glove", "n.", "手套", "Basic"));
            initialVocabs.add(new Vocab("Bag", "n.", "袋子", "Basic"));
            initialVocabs.add(new Vocab("Pocket", "n.", "口袋", "Basic"));
            initialVocabs.add(new Vocab("Ring", "n.", "戒指", "Basic"));
            initialVocabs.add(new Vocab("Watch", "n.", "手錶", "Basic"));
            initialVocabs.add(new Vocab("Belt", "n.", "皮帶", "Basic"));
            initialVocabs.add(new Vocab("Jacket", "n.", "夾克", "Basic"));
            initialVocabs.add(new Vocab("Glasses", "n.", "眼鏡", "Basic"));
            initialVocabs.add(new Vocab("Wallet", "n.", "錢包", "Basic"));
            initialVocabs.add(new Vocab("Umbrella", "n.", "雨傘", "Basic"));
            initialVocabs.add(new Vocab("Suit", "n.", "西裝", "Basic"));
            initialVocabs.add(new Vocab("Wear", "v.", "穿著", "Basic"));

// --- 食物與飲料 (25) ---
            initialVocabs.add(new Vocab("Milk", "n.", "牛奶", "Basic"));
            initialVocabs.add(new Vocab("Egg", "n.", "蛋", "Basic"));
            initialVocabs.add(new Vocab("Rice", "n.", "米飯", "Basic"));
            initialVocabs.add(new Vocab("Meat", "n.", "肉", "Basic"));
            initialVocabs.add(new Vocab("Fish", "n.", "魚", "Basic"));
            initialVocabs.add(new Vocab("Juice", "n.", "果汁", "Basic"));
            initialVocabs.add(new Vocab("Tea", "n.", "茶", "Basic"));
            initialVocabs.add(new Vocab("Coffee", "n.", "咖啡", "Basic"));
            initialVocabs.add(new Vocab("Cake", "n.", "蛋糕", "Basic"));
            initialVocabs.add(new Vocab("Sugar", "n.", "糖", "Basic"));
            initialVocabs.add(new Vocab("Salt", "n.", "鹽", "Basic"));
            initialVocabs.add(new Vocab("Soup", "n.", "湯", "Basic"));
            initialVocabs.add(new Vocab("Fruit", "n.", "水果", "Basic"));
            initialVocabs.add(new Vocab("Potato", "n.", "馬鈴薯", "Basic"));
            initialVocabs.add(new Vocab("Tomato", "n.", "番茄", "Basic"));
            initialVocabs.add(new Vocab("Chicken", "n.", "雞肉", "Basic"));
            initialVocabs.add(new Vocab("Bread", "n.", "麵包", "Basic"));
            initialVocabs.add(new Vocab("Butter", "n.", "奶油", "Basic"));
            initialVocabs.add(new Vocab("Cheese", "n.", "起司", "Basic"));
            initialVocabs.add(new Vocab("Candy", "n.", "糖果", "Basic"));
            initialVocabs.add(new Vocab("Dinner", "n.", "晚餐", "Basic"));
            initialVocabs.add(new Vocab("Lunch", "n.", "午餐", "Basic"));
            initialVocabs.add(new Vocab("Breakfast", "n.", "早餐", "Basic"));
            initialVocabs.add(new Vocab("Cook", "v.", "烹飪", "Basic"));
            initialVocabs.add(new Vocab("Drink", "v.", "喝", "Basic"));

// --- 動物與昆蟲 (20) ---
            initialVocabs.add(new Vocab("Bird", "n.", "鳥", "Basic"));
            initialVocabs.add(new Vocab("Horse", "n.", "馬", "Basic"));
            initialVocabs.add(new Vocab("Cow", "n.", "母牛", "Basic"));
            initialVocabs.add(new Vocab("Pig", "n.", "豬", "Basic"));
            initialVocabs.add(new Vocab("Sheep", "n.", "綿羊", "Basic"));
            initialVocabs.add(new Vocab("Rabbit", "n.", "兔子", "Basic"));
            initialVocabs.add(new Vocab("Monkey", "n.", "猴子", "Basic"));
            initialVocabs.add(new Vocab("Lion", "n.", "獅子", "Basic"));
            initialVocabs.add(new Vocab("Tiger", "n.", "老虎", "Basic"));
            initialVocabs.add(new Vocab("Elephant", "n.", "大象", "Basic"));
            initialVocabs.add(new Vocab("Bear", "n.", "熊", "Basic"));
            initialVocabs.add(new Vocab("Snake", "n.", "蛇", "Basic"));
            initialVocabs.add(new Vocab("Mouse", "n.", "老鼠", "Basic"));
            initialVocabs.add(new Vocab("Duck", "n.", "鴨子", "Basic"));
            initialVocabs.add(new Vocab("Frog", "n.", "青蛙", "Basic"));
            initialVocabs.add(new Vocab("Bee", "n.", "蜜蜂", "Basic"));
            initialVocabs.add(new Vocab("Ant", "n.", "螞蟻", "Basic"));
            initialVocabs.add(new Vocab("Spider", "n.", "蜘蛛", "Basic"));
            initialVocabs.add(new Vocab("Fly", "n.", "蒼蠅", "Basic"));
            initialVocabs.add(new Vocab("Pet", "n.", "寵物", "Basic"));

// --- 自然、天氣與地點 (25) ---
            initialVocabs.add(new Vocab("Sun", "n.", "太陽", "Basic"));
            initialVocabs.add(new Vocab("Moon", "n.", "月亮", "Basic"));
            initialVocabs.add(new Vocab("Star", "n.", "星星", "Basic"));
            initialVocabs.add(new Vocab("Sky", "n.", "天空", "Basic"));
            initialVocabs.add(new Vocab("Tree", "n.", "樹", "Basic"));
            initialVocabs.add(new Vocab("Leaf", "n.", "葉子", "Basic"));
            initialVocabs.add(new Vocab("Grass", "n.", "草", "Basic"));
            initialVocabs.add(new Vocab("Cloud", "n.", "雲", "Basic"));
            initialVocabs.add(new Vocab("Wind", "n.", "風", "Basic"));
            initialVocabs.add(new Vocab("Snow", "n.", "雪", "Basic"));
            initialVocabs.add(new Vocab("Mountain", "n.", "山", "Basic"));
            initialVocabs.add(new Vocab("River", "n.", "河流", "Basic"));
            initialVocabs.add(new Vocab("Sea", "n.", "海洋", "Basic"));
            initialVocabs.add(new Vocab("Beach", "n.", "海灘", "Basic"));
            initialVocabs.add(new Vocab("Island", "n.", "島嶼", "Basic"));
            initialVocabs.add(new Vocab("Earth", "n.", "地球", "Basic"));
            initialVocabs.add(new Vocab("Air", "n.", "空氣", "Basic"));
            initialVocabs.add(new Vocab("Hot", "adj.", "熱的", "Basic"));
            initialVocabs.add(new Vocab("Cold", "adj.", "冷的", "Basic"));
            initialVocabs.add(new Vocab("City", "n.", "城市", "Basic"));
            initialVocabs.add(new Vocab("Town", "n.", "城鎮", "Basic"));
            initialVocabs.add(new Vocab("Park", "n.", "公園", "Basic"));
            initialVocabs.add(new Vocab("Street", "n.", "街道", "Basic"));
            initialVocabs.add(new Vocab("Shop", "n.", "商店", "Basic"));
            initialVocabs.add(new Vocab("Bank", "n.", "銀行", "Basic"));

// --- 居家物品 (20) ---
            initialVocabs.add(new Vocab("Bed", "n.", "床", "Basic"));
            initialVocabs.add(new Vocab("Chair", "n.", "椅子", "Basic"));
            initialVocabs.add(new Vocab("Door", "n.", "門", "Basic"));
            initialVocabs.add(new Vocab("Floor", "n.", "地板", "Basic"));
            initialVocabs.add(new Vocab("Wall", "n.", "牆壁", "Basic"));
            initialVocabs.add(new Vocab("Key", "n.", "鑰匙", "Basic"));
            initialVocabs.add(new Vocab("Cup", "n.", "杯子", "Basic"));
            initialVocabs.add(new Vocab("Plate", "n.", "盤子", "Basic"));
            initialVocabs.add(new Vocab("Spoon", "n.", "湯匙", "Basic"));
            initialVocabs.add(new Vocab("Knife", "n.", "刀子", "Basic"));
            initialVocabs.add(new Vocab("Fork", "n.", "叉子", "Basic"));
            initialVocabs.add(new Vocab("Bowl", "n.", "碗", "Basic"));
            initialVocabs.add(new Vocab("Clock", "n.", "時鐘", "Basic"));
            initialVocabs.add(new Vocab("Lamp", "n.", "燈", "Basic"));
            initialVocabs.add(new Vocab("Mirror", "n.", "鏡子", "Basic"));
            initialVocabs.add(new Vocab("Soap", "n.", "肥皂", "Basic"));
            initialVocabs.add(new Vocab("Towell", "n.", "毛巾", "Basic"));
            initialVocabs.add(new Vocab("Phone", "n.", "電話", "Basic"));
            initialVocabs.add(new Vocab("Box", "n.", "箱子", "Basic"));
            initialVocabs.add(new Vocab("TV", "n.", "電視", "Basic"));

// --- 常用動作 (30) ---
            initialVocabs.add(new Vocab("Walk", "v.", "走路", "Basic"));
            initialVocabs.add(new Vocab("Read", "v.", "閱讀", "Basic"));
            initialVocabs.add(new Vocab("Write", "v.", "寫", "Basic"));
            initialVocabs.add(new Vocab("Sing", "v.", "唱歌", "Basic"));
            initialVocabs.add(new Vocab("Swim", "v.", "游泳", "Basic"));
            initialVocabs.add(new Vocab("Help", "v.", "幫助", "Basic"));
            initialVocabs.add(new Vocab("Talk", "v.", "說話", "Basic"));
            initialVocabs.add(new Vocab("Look", "v.", "看", "Basic"));
            initialVocabs.add(new Vocab("Listen", "v.", "聽", "Basic"));
            initialVocabs.add(new Vocab("Sit", "v.", "坐下", "Basic"));
            initialVocabs.add(new Vocab("Stand", "v.", "站立", "Basic"));
            initialVocabs.add(new Vocab("Wait", "v.", "等待", "Basic"));
            initialVocabs.add(new Vocab("Stop", "v.", "停止", "Basic"));
            initialVocabs.add(new Vocab("Go", "v.", "去", "Basic"));
            initialVocabs.add(new Vocab("Come", "v.", "來", "Basic"));
            initialVocabs.add(new Vocab("Give", "v.", "給予", "Basic"));
            initialVocabs.add(new Vocab("Take", "v.", "拿取", "Basic"));
            initialVocabs.add(new Vocab("Make", "v.", "製造", "Basic"));
            initialVocabs.add(new Vocab("Use", "v.", "使用", "Basic"));
            initialVocabs.add(new Vocab("Find", "v.", "尋找", "Basic"));
            initialVocabs.add(new Vocab("Keep", "v.", "保持", "Basic"));
            initialVocabs.add(new Vocab("Hold", "v.", "握住", "Basic"));
            initialVocabs.add(new Vocab("Buy", "v.", "購買", "Basic"));
            initialVocabs.add(new Vocab("Sell", "v.", "販賣", "Basic"));
            initialVocabs.add(new Vocab("Pull", "v.", "拉", "Basic"));
            initialVocabs.add(new Vocab("Push", "v.", "推", "Basic"));
            initialVocabs.add(new Vocab("Cry", "v.", "哭泣", "Basic"));
            initialVocabs.add(new Vocab("Laugh", "v.", "笑", "Basic"));
            initialVocabs.add(new Vocab("Ask", "v.", "詢問", "Basic"));
            initialVocabs.add(new Vocab("Tell", "v.", "告訴", "Basic"));

// --- 形容詞與特徵 (20) ---
            initialVocabs.add(new Vocab("Big", "adj.", "大的", "Basic"));
            initialVocabs.add(new Vocab("Small", "adj.", "小的", "Basic"));
            initialVocabs.add(new Vocab("Tall", "adj.", "高的", "Basic"));
            initialVocabs.add(new Vocab("Short", "adj.", "矮的", "Basic"));
            initialVocabs.add(new Vocab("Long", "adj.", "長的", "Basic"));
            initialVocabs.add(new Vocab("Strong", "adj.", "強壯的", "Basic"));
            initialVocabs.add(new Vocab("Weak", "adj.", "虛弱的", "Basic"));
            initialVocabs.add(new Vocab("Fast", "adj.", "快的", "Basic"));
            initialVocabs.add(new Vocab("Slow", "adj.", "慢的", "Basic"));
            initialVocabs.add(new Vocab("New", "adj.", "新的", "Basic"));
            initialVocabs.add(new Vocab("Old", "adj.", "舊的", "Basic"));
            initialVocabs.add(new Vocab("Good", "adj.", "好的", "Basic"));
            initialVocabs.add(new Vocab("Bad", "adj.", "壞的", "Basic"));
            initialVocabs.add(new Vocab("Dark", "adj.", "黑暗的", "Basic"));
            initialVocabs.add(new Vocab("Bright", "adj.", "明亮的", "Basic"));
            initialVocabs.add(new Vocab("Soft", "adj.", "柔軟的", "Basic"));
            initialVocabs.add(new Vocab("Hard", "adj.", "堅硬的", "Basic"));
            initialVocabs.add(new Vocab("Rich", "adj.", "富有的", "Basic"));
            initialVocabs.add(new Vocab("Poor", "adj.", "貧窮的", "Basic"));
            initialVocabs.add(new Vocab("Cheap", "adj.", "便宜的", "Basic"));

// --- 時間、數字與人物 (20) ---
            initialVocabs.add(new Vocab("One", "num.", "一", "Basic"));
            initialVocabs.add(new Vocab("Ten", "num.", "十", "Basic"));
            initialVocabs.add(new Vocab("Hundred", "num.", "百", "Basic"));
            initialVocabs.add(new Vocab("Year", "n.", "年", "Basic"));
            initialVocabs.add(new Vocab("Month", "n.", "月", "Basic"));
            initialVocabs.add(new Vocab("Week", "n.", "星期", "Basic"));
            initialVocabs.add(new Vocab("Day", "n.", "天", "Basic"));
            initialVocabs.add(new Vocab("Hour", "n.", "小時", "Basic"));
            initialVocabs.add(new Vocab("Minute", "n.", "分鐘", "Basic"));
            initialVocabs.add(new Vocab("Today", "n.", "今天", "Basic"));
            initialVocabs.add(new Vocab("Man", "n.", "男人", "Basic"));
            initialVocabs.add(new Vocab("Woman", "n.", "女人", "Basic"));
            initialVocabs.add(new Vocab("Boy", "n.", "男孩", "Basic"));
            initialVocabs.add(new Vocab("Girl", "n.", "女孩", "Basic"));
            initialVocabs.add(new Vocab("Baby", "n.", "嬰兒", "Basic"));
            initialVocabs.add(new Vocab("Doctor", "n.", "醫生", "Basic"));
            initialVocabs.add(new Vocab("Nurse", "n.", "護士", "Basic"));
            initialVocabs.add(new Vocab("Student", "n.", "學生", "Basic"));
            initialVocabs.add(new Vocab("Police", "n.", "警察", "Basic"));
            initialVocabs.add(new Vocab("Driver", "n.", "司機", "Basic"));
            //End of Basic
            // // // // // // // // // // // // //






            // Enhanced Mode
            initialVocabs.add(new Vocab("Achieve", "v.", "達成", "Enhanced"));
            initialVocabs.add(new Vocab("Believe", "v.", "相信", "Enhanced"));
            initialVocabs.add(new Vocab("Challenge", "n.", "挑戰", "Enhanced"));
            initialVocabs.add(new Vocab("Decide", "v.", "決定", "Enhanced"));
            initialVocabs.add(new Vocab("Energy", "n.", "能量", "Enhanced"));
            initialVocabs.add(new Vocab("Feature", "n.", "特徵", "Enhanced"));
            initialVocabs.add(new Vocab("Global", "adj.", "全球的", "Enhanced"));
            initialVocabs.add(new Vocab("Healthy", "adj.", "健康的", "Enhanced"));
            initialVocabs.add(new Vocab("Impact", "n.", "影響", "Enhanced"));
            initialVocabs.add(new Vocab("Journey", "n.", "旅程", "Enhanced"));
            initialVocabs.add(new Vocab("Knowledge", "n.", "知識", "Enhanced"));
            initialVocabs.add(new Vocab("Logical", "adj.", "邏輯的", "Enhanced"));
            initialVocabs.add(new Vocab("Manage", "v.", "管理", "Enhanced"));
            initialVocabs.add(new Vocab("Network", "n.", "網絡", "Enhanced"));
            initialVocabs.add(new Vocab("Object", "n.", "物體", "Enhanced"));
            initialVocabs.add(new Vocab("Patient", "adj.", "耐心的", "Enhanced"));
            initialVocabs.add(new Vocab("Quality", "n.", "質量", "Enhanced"));
            initialVocabs.add(new Vocab("Recent", "adj.", "最近的", "Enhanced"));
            initialVocabs.add(new Vocab("Success", "n.", "成功", "Enhanced"));
            initialVocabs.add(new Vocab("Theory", "n.", "理論", "Enhanced"));
            initialVocabs.add(new Vocab("Unique", "adj.", "獨特的", "Enhanced"));
            initialVocabs.add(new Vocab("Various", "adj.", "各種各樣的", "Enhanced"));
            initialVocabs.add(new Vocab("Wealth", "n.", "財富", "Enhanced"));
            initialVocabs.add(new Vocab("Ability", "n.", "能力", "Enhanced"));
            initialVocabs.add(new Vocab("Benefit", "n.", "利益", "Enhanced"));
            initialVocabs.add(new Vocab("Career", "n.", "職業", "Enhanced"));
            initialVocabs.add(new Vocab("Degree", "n.", "程度 / 學位", "Enhanced"));
            initialVocabs.add(new Vocab("Effort", "n.", "努力", "Enhanced"));
            initialVocabs.add(new Vocab("Focus", "v.", "集中", "Enhanced"));
            initialVocabs.add(new Vocab("Growth", "n.", "增長", "Enhanced"));
            initialVocabs.add(new Vocab("Honest", "adj.", "誠實的", "Enhanced"));
            initialVocabs.add(new Vocab("Image", "n.", "圖像", "Enhanced"));
            initialVocabs.add(new Vocab("Judge", "v.", "判斷", "Enhanced"));
            initialVocabs.add(new Vocab("Likely", "adv.", "很可能地", "Enhanced"));
            initialVocabs.add(new Vocab("Method", "n.", "方法", "Enhanced"));
            initialVocabs.add(new Vocab("Notice", "v.", "注意", "Enhanced"));
            initialVocabs.add(new Vocab("Option", "n.", "選項", "Enhanced"));
            initialVocabs.add(new Vocab("Period", "n.", "時段", "Enhanced"));
            initialVocabs.add(new Vocab("Reason", "n.", "原因", "Enhanced"));
            initialVocabs.add(new Vocab("Skill", "n.", "技能", "Enhanced"));
            initialVocabs.add(new Vocab("Target", "n.", "目標", "Enhanced"));
            initialVocabs.add(new Vocab("Useful", "adj.", "有用的", "Enhanced"));
            initialVocabs.add(new Vocab("Value", "n.", "價值", "Enhanced"));
            initialVocabs.add(new Vocab("Writer", "n.", "作者", "Enhanced"));
            initialVocabs.add(new Vocab("Adapt", "v.", "適應", "Enhanced"));
            initialVocabs.add(new Vocab("Budget", "n.", "預算", "Enhanced"));
            initialVocabs.add(new Vocab("Create", "v.", "創造", "Enhanced"));
            initialVocabs.add(new Vocab("Device", "n.", "裝置", "Enhanced"));
            initialVocabs.add(new Vocab("Expert", "n.", "專家", "Enhanced"));
            initialVocabs.add(new Vocab("Factor", "n.", "因素", "Enhanced"));
            initialVocabs.add(new Vocab("Gather", "v.", "聚集", "Enhanced"));
            initialVocabs.add(new Vocab("Hazard", "n.", "危險", "Enhanced"));
            initialVocabs.add(new Vocab("Ignore", "v.", "忽略", "Enhanced"));
            initialVocabs.add(new Vocab("Junior", "adj.", "資歷較淺的", "Enhanced"));
            initialVocabs.add(new Vocab("Laptop", "n.", "筆記本電腦", "Enhanced"));
            initialVocabs.add(new Vocab("Memory", "n.", "記憶", "Enhanced"));
            initialVocabs.add(new Vocab("Native", "adj.", "本地的", "Enhanced"));
            initialVocabs.add(new Vocab("Output", "n.", "輸出", "Enhanced"));
            initialVocabs.add(new Vocab("Policy", "n.", "政策", "Enhanced"));
            initialVocabs.add(new Vocab("Reform", "v.", "改革", "Enhanced"));
            initialVocabs.add(new Vocab("Signal", "n.", "信號", "Enhanced"));
            initialVocabs.add(new Vocab("Trend", "n.", "趨勢", "Enhanced"));
            initialVocabs.add(new Vocab("Update", "v.", "更新", "Enhanced"));
            initialVocabs.add(new Vocab("Visual", "adj.", "視覺的", "Enhanced"));
            initialVocabs.add(new Vocab("Witness", "n.", "目擊者", "Enhanced"));
            initialVocabs.add(new Vocab("Yield", "v.", "產生 / 屈服", "Enhanced"));
            initialVocabs.add(new Vocab("Active", "adj.", "活躍的", "Enhanced"));
            initialVocabs.add(new Vocab("Brief", "adj.", "簡短的", "Enhanced"));
            initialVocabs.add(new Vocab("Client", "n.", "客戶", "Enhanced"));
            initialVocabs.add(new Vocab("Detail", "n.", "細節", "Enhanced"));

            // --- Business & Professional (20) ---
            initialVocabs.add(new Vocab("Propose", "v.", "提議", "Enhanced"));
            initialVocabs.add(new Vocab("Expand", "v.", "擴展", "Enhanced"));
            initialVocabs.add(new Vocab("Negotiate", "v.", "談判", "Enhanced"));
            initialVocabs.add(new Vocab("Contract", "n.", "合約", "Enhanced"));
            initialVocabs.add(new Vocab("Profit", "n.", "利潤", "Enhanced"));
            initialVocabs.add(new Vocab("Invest", "v.", "投資", "Enhanced"));
            initialVocabs.add(new Vocab("Strategy", "n.", "策略", "Enhanced"));
            initialVocabs.add(new Vocab("Promote", "v.", "晉升", "Enhanced"));
            initialVocabs.add(new Vocab("Purchase", "v.", "購買", "Enhanced"));
            initialVocabs.add(new Vocab("Demand", "n.", "需求", "Enhanced"));
            initialVocabs.add(new Vocab("Supply", "v.", "供應", "Enhanced"));
            initialVocabs.add(new Vocab("Market", "v.", "行銷", "Enhanced"));
            initialVocabs.add(new Vocab("Brand", "n.", "品牌", "Enhanced"));
            initialVocabs.add(new Vocab("Leader", "n.", "領導者", "Enhanced"));
            initialVocabs.add(new Vocab("Motivate", "v.", "激勵", "Enhanced"));
            initialVocabs.add(new Vocab("Evaluate", "v.", "評估", "Enhanced"));
            initialVocabs.add(new Vocab("Feedback", "n.", "回饋", "Enhanced"));
            initialVocabs.add(new Vocab("Schedule", "n.", "日程表", "Enhanced"));
            initialVocabs.add(new Vocab("Deadline", "n.", "截止日期", "Enhanced"));
            initialVocabs.add(new Vocab("Project", "n.", "專案", "Enhanced"));

// --- Workplace & Tasks (20) ---
            initialVocabs.add(new Vocab("Task", "n.", "任務", "Enhanced"));
            initialVocabs.add(new Vocab("Goal", "n.", "目標", "Enhanced"));
            initialVocabs.add(new Vocab("Objective", "n.", "目標", "Enhanced"));
            initialVocabs.add(new Vocab("Direct", "v.", "指導", "Enhanced"));
            initialVocabs.add(new Vocab("Partner", "n.", "夥伴", "Enhanced"));
            initialVocabs.add(new Vocab("Colleague", "n.", "同事", "Enhanced"));
            initialVocabs.add(new Vocab("Employ", "v.", "雇用", "Enhanced"));
            initialVocabs.add(new Vocab("Dismiss", "v.", "解雇", "Enhanced"));
            initialVocabs.add(new Vocab("Resume", "n.", "履歷", "Enhanced"));
            initialVocabs.add(new Vocab("Interview", "n.", "面試", "Enhanced"));
            initialVocabs.add(new Vocab("Salary", "n.", "薪水", "Enhanced"));
            initialVocabs.add(new Vocab("Finance", "n.", "財務", "Enhanced"));
            initialVocabs.add(new Vocab("Account", "n.", "帳戶", "Enhanced"));
            initialVocabs.add(new Vocab("Branch", "n.", "分店", "Enhanced"));
            initialVocabs.add(new Vocab("Agency", "n.", "代理機構", "Enhanced"));
            initialVocabs.add(new Vocab("Analyze", "v.", "分析", "Enhanced"));
            initialVocabs.add(new Vocab("Research", "n.", "研究", "Enhanced"));
            initialVocabs.add(new Vocab("Explore", "v.", "探索", "Enhanced"));
            initialVocabs.add(new Vocab("Discover", "v.", "發現", "Enhanced"));
            initialVocabs.add(new Vocab("Invent", "v.", "發明", "Enhanced"));

// --- Science & Technology (20) ---
            initialVocabs.add(new Vocab("Concept", "n.", "概念", "Enhanced"));
            initialVocabs.add(new Vocab("Digital", "adj.", "數位的", "Enhanced"));
            initialVocabs.add(new Vocab("System", "n.", "系統", "Enhanced"));
            initialVocabs.add(new Vocab("Process", "n.", "過程", "Enhanced"));
            initialVocabs.add(new Vocab("Measure", "v.", "測量", "Enhanced"));
            initialVocabs.add(new Vocab("Record", "v.", "記錄", "Enhanced"));
            initialVocabs.add(new Vocab("Lab", "n.", "實驗室", "Enhanced"));
            initialVocabs.add(new Vocab("Sample", "n.", "樣本", "Enhanced"));
            initialVocabs.add(new Vocab("Element", "n.", "元素", "Enhanced"));
            initialVocabs.add(new Vocab("Carbon", "n.", "碳", "Enhanced"));
            initialVocabs.add(new Vocab("Data", "n.", "數據", "Enhanced"));
            initialVocabs.add(new Vocab("Program", "v.", "編程", "Enhanced"));
            initialVocabs.add(new Vocab("Error", "n.", "錯誤", "Enhanced"));
            initialVocabs.add(new Vocab("Access", "n.", "存取權", "Enhanced"));
            initialVocabs.add(new Vocab("Gravity", "n.", "重力", "Enhanced"));
            initialVocabs.add(new Vocab("Orbit", "n.", "軌道", "Enhanced"));
            initialVocabs.add(new Vocab("Satellite", "n.", "衛星", "Enhanced"));
            initialVocabs.add(new Vocab("Space", "n.", "太空", "Enhanced"));
            initialVocabs.add(new Vocab("Planet", "n.", "行星", "Enhanced"));
            initialVocabs.add(new Vocab("Climate", "n.", "氣候", "Enhanced"));

// --- Nature & Environment (20) ---
            initialVocabs.add(new Vocab("Pollution", "n.", "污染", "Enhanced"));
            initialVocabs.add(new Vocab("Protect", "v.", "保護", "Enhanced"));
            initialVocabs.add(new Vocab("Recycle", "v.", "回收", "Enhanced"));
            initialVocabs.add(new Vocab("Source", "n.", "來源", "Enhanced"));
            initialVocabs.add(new Vocab("Species", "n.", "物種", "Enhanced"));
            initialVocabs.add(new Vocab("Creature", "n.", "生物", "Enhanced"));
            initialVocabs.add(new Vocab("Habitat", "n.", "棲息地", "Enhanced"));
            initialVocabs.add(new Vocab("Wild", "adj.", "野生的", "Enhanced"));
            initialVocabs.add(new Vocab("Forest", "n.", "森林", "Enhanced"));
            initialVocabs.add(new Vocab("Ocean", "n.", "海洋", "Enhanced"));
            initialVocabs.add(new Vocab("Empathy", "n.", "同理心", "Enhanced"));
            initialVocabs.add(new Vocab("Emotion", "n.", "情感", "Enhanced"));
            initialVocabs.add(new Vocab("Confident", "adj.", "自信的", "Enhanced"));
            initialVocabs.add(new Vocab("Anxious", "adj.", "焦慮的", "Enhanced"));
            initialVocabs.add(new Vocab("Stress", "n.", "壓力", "Enhanced"));
            initialVocabs.add(new Vocab("Relieve", "v.", "緩解", "Enhanced"));
            initialVocabs.add(new Vocab("Support", "v.", "支持", "Enhanced"));
            initialVocabs.add(new Vocab("Comfort", "n.", "安慰", "Enhanced"));
            initialVocabs.add(new Vocab("Inspire", "v.", "啟發", "Enhanced"));
            initialVocabs.add(new Vocab("Courage", "n.", "勇氣", "Enhanced"));

// --- Society & Mind (20) ---
            initialVocabs.add(new Vocab("Respect", "v.", "尊重", "Enhanced"));
            initialVocabs.add(new Vocab("Admire", "v.", "欽佩", "Enhanced"));
            initialVocabs.add(new Vocab("Forgive", "v.", "原諒", "Enhanced"));
            initialVocabs.add(new Vocab("Grateful", "adj.", "感激的", "Enhanced"));
            initialVocabs.add(new Vocab("Jealous", "adj.", "嫉妒的", "Enhanced"));
            initialVocabs.add(new Vocab("Proud", "adj.", "自豪的", "Enhanced"));
            initialVocabs.add(new Vocab("Culture", "n.", "文化", "Enhanced"));
            initialVocabs.add(new Vocab("Society", "n.", "社會", "Enhanced"));
            initialVocabs.add(new Vocab("Community", "n.", "社區", "Enhanced"));
            initialVocabs.add(new Vocab("Tradition", "n.", "傳統", "Enhanced"));
            initialVocabs.add(new Vocab("Custom", "n.", "習俗", "Enhanced"));
            initialVocabs.add(new Vocab("Moral", "adj.", "道德的", "Enhanced"));
            initialVocabs.add(new Vocab("Faith", "n.", "信仰", "Enhanced"));
            initialVocabs.add(new Vocab("Values", "n.", "價值觀", "Enhanced"));
            initialVocabs.add(new Vocab("Rights", "n.", "權利", "Enhanced"));
            initialVocabs.add(new Vocab("Law", "n.", "法律", "Enhanced"));
            initialVocabs.add(new Vocab("Freedom", "n.", "自由", "Enhanced"));
            initialVocabs.add(new Vocab("Justice", "n.", "正義", "Enhanced"));
            initialVocabs.add(new Vocab("Peace", "n.", "和平", "Enhanced"));
            initialVocabs.add(new Vocab("Conflict", "n.", "衝突", "Enhanced"));

// --- Abstract & Measurement (20) ---
            initialVocabs.add(new Vocab("Debate", "v.", "辯論", "Enhanced"));
            initialVocabs.add(new Vocab("Agree", "v.", "同意", "Enhanced"));
            initialVocabs.add(new Vocab("Dispute", "n.", "爭端", "Enhanced"));
            initialVocabs.add(new Vocab("Resolve", "v.", "解決", "Enhanced"));
            initialVocabs.add(new Vocab("Harmony", "n.", "和諧", "Enhanced"));
            initialVocabs.add(new Vocab("Infinite", "adj.", "無限的", "Enhanced"));
            initialVocabs.add(new Vocab("Maximum", "adj.", "最大的", "Enhanced"));
            initialVocabs.add(new Vocab("Minimum", "adj.", "最小的", "Enhanced"));
            initialVocabs.add(new Vocab("Average", "n.", "平均", "Enhanced"));
            initialVocabs.add(new Vocab("Portion", "n.", "部分", "Enhanced"));
            initialVocabs.add(new Vocab("Total", "adj.", "總共的", "Enhanced"));
            initialVocabs.add(new Vocab("Double", "v.", "翻倍", "Enhanced"));
            initialVocabs.add(new Vocab("Reduce", "v.", "減少", "Enhanced"));
            initialVocabs.add(new Vocab("Increase", "v.", "增加", "Enhanced"));
            initialVocabs.add(new Vocab("Rapid", "adj.", "迅速的", "Enhanced"));
            initialVocabs.add(new Vocab("Sudden", "adj.", "突然的", "Enhanced"));
            initialVocabs.add(new Vocab("Constant", "adj.", "持續的", "Enhanced"));
            initialVocabs.add(new Vocab("Stable", "adj.", "穩定的", "Enhanced"));
            initialVocabs.add(new Vocab("Diverse", "adj.", "多樣的", "Enhanced"));
            initialVocabs.add(new Vocab("Complex", "adj.", "複雜的", "Enhanced"));

// --- Qualities & Characteristics (20) ---
            initialVocabs.add(new Vocab("Genuine", "adj.", "真誠的", "Enhanced"));
            initialVocabs.add(new Vocab("Abstract", "adj.", "抽象的", "Enhanced"));
            initialVocabs.add(new Vocab("Concrete", "adj.", "具體的", "Enhanced"));
            initialVocabs.add(new Vocab("Ancient", "adj.", "古代的", "Enhanced"));
            initialVocabs.add(new Vocab("Modern", "adj.", "現代的", "Enhanced"));
            initialVocabs.add(new Vocab("Future", "n.", "未來", "Enhanced"));
            initialVocabs.add(new Vocab("Standard", "n.", "標準", "Enhanced"));
            initialVocabs.add(new Vocab("Ideal", "adj.", "理想的", "Enhanced"));
            initialVocabs.add(new Vocab("Normal", "adj.", "正常的", "Enhanced"));
            initialVocabs.add(new Vocab("Odd", "adj.", "奇怪的", "Enhanced"));
            initialVocabs.add(new Vocab("Rare", "adj.", "罕見的", "Enhanced"));
            initialVocabs.add(new Vocab("Common", "adj.", "常見的", "Enhanced"));
            initialVocabs.add(new Vocab("Famous", "adj.", "著名的", "Enhanced"));
            initialVocabs.add(new Vocab("Mystery", "n.", "謎", "Enhanced"));
            initialVocabs.add(new Vocab("Secret", "n.", "秘密", "Enhanced"));
            initialVocabs.add(new Vocab("Symbol", "n.", "象徵", "Enhanced"));
            initialVocabs.add(new Vocab("Pattern", "n.", "模式", "Enhanced"));
            initialVocabs.add(new Vocab("Style", "n.", "風格", "Enhanced"));
            initialVocabs.add(new Vocab("Form", "n.", "形式", "Enhanced"));
            initialVocabs.add(new Vocab("Structure", "n.", "結構", "Enhanced"));

// --- Actions & Verbs (20) ---
            initialVocabs.add(new Vocab("Maintain", "v.", "維持", "Enhanced"));
            initialVocabs.add(new Vocab("Prevent", "v.", "預防", "Enhanced"));
            initialVocabs.add(new Vocab("Defend", "v.", "防衛", "Enhanced"));
            initialVocabs.add(new Vocab("Attack", "v.", "攻擊", "Enhanced"));
            initialVocabs.add(new Vocab("Escape", "v.", "逃跑", "Enhanced"));
            initialVocabs.add(new Vocab("Pursue", "v.", "追求", "Enhanced"));
            initialVocabs.add(new Vocab("Accomplish", "v.", "完成", "Enhanced"));
            initialVocabs.add(new Vocab("Complete", "v.", "完成", "Enhanced"));
            initialVocabs.add(new Vocab("Prepare", "v.", "準備", "Enhanced"));
            initialVocabs.add(new Vocab("Arrange", "v.", "安排", "Enhanced"));
            initialVocabs.add(new Vocab("Organize", "v.", "組織", "Enhanced"));
            initialVocabs.add(new Vocab("Control", "v.", "控制", "Enhanced"));
            initialVocabs.add(new Vocab("Guide", "v.", "引導", "Enhanced"));
            initialVocabs.add(new Vocab("Influence", "v.", "影響", "Enhanced"));
            initialVocabs.add(new Vocab("Affect", "v.", "影響", "Enhanced"));
            initialVocabs.add(new Vocab("Effect", "n.", "效果", "Enhanced"));
            initialVocabs.add(new Vocab("Produce", "v.", "生產", "Enhanced"));
            initialVocabs.add(new Vocab("Consumer", "n.", "消費者", "Enhanced"));
            initialVocabs.add(new Vocab("Deliver", "v.", "遞送", "Enhanced"));
            initialVocabs.add(new Vocab("Receive", "v.", "接收", "Enhanced"));

// --- Decisions & Communication (20) ---
            initialVocabs.add(new Vocab("Accept", "v.", "接受", "Enhanced"));
            initialVocabs.add(new Vocab("Reject", "v.", "拒絕", "Enhanced"));
            initialVocabs.add(new Vocab("Refuse", "v.", "拒絕", "Enhanced"));
            initialVocabs.add(new Vocab("Insist", "v.", "堅持", "Enhanced"));
            initialVocabs.add(new Vocab("Persuade", "v.", "說服", "Enhanced"));
            initialVocabs.add(new Vocab("Suggest", "v.", "建議", "Enhanced"));
            initialVocabs.add(new Vocab("Advise", "v.", "勸告", "Enhanced"));
            initialVocabs.add(new Vocab("Recommend", "v.", "推薦", "Enhanced"));
            initialVocabs.add(new Vocab("Remind", "v.", "提醒", "Enhanced"));
            initialVocabs.add(new Vocab("Warn", "v.", "警告", "Enhanced"));
            initialVocabs.add(new Vocab("Severe", "adj.", "嚴重的", "Enhanced"));
            initialVocabs.add(new Vocab("Gentle", "adj.", "溫和的", "Enhanced"));
            initialVocabs.add(new Vocab("Smooth", "adj.", "光滑的", "Enhanced"));
            initialVocabs.add(new Vocab("Rough", "adj.", "粗糙的", "Enhanced"));
            initialVocabs.add(new Vocab("Solid", "adj.", "固體的", "Enhanced"));
            initialVocabs.add(new Vocab("Liquid", "n.", "液體", "Enhanced"));
            initialVocabs.add(new Vocab("Gas", "n.", "氣體", "Enhanced"));
            initialVocabs.add(new Vocab("Empty", "adj.", "空的", "Enhanced"));
            initialVocabs.add(new Vocab("Full", "adj.", "滿的", "Enhanced"));
            initialVocabs.add(new Vocab("Heavy", "adj.", "重的", "Enhanced"));

// --- Descriptions & Personalities (20) ---
            initialVocabs.add(new Vocab("Slight", "adj.", "輕微的", "Enhanced"));
            initialVocabs.add(new Vocab("Tiny", "adj.", "極小的", "Enhanced"));
            initialVocabs.add(new Vocab("Vast", "adj.", "巨大的", "Enhanced"));
            initialVocabs.add(new Vocab("Enormous", "adj.", "龐大的", "Enhanced"));
            initialVocabs.add(new Vocab("Broad", "adj.", "寬闊的", "Enhanced"));
            initialVocabs.add(new Vocab("Narrow", "adj.", "狹窄的", "Enhanced"));
            initialVocabs.add(new Vocab("Deep", "adj.", "深的", "Enhanced"));
            initialVocabs.add(new Vocab("Shallow", "adj.", "淺的", "Enhanced"));
            initialVocabs.add(new Vocab("Pure", "adj.", "純淨的", "Enhanced"));
            initialVocabs.add(new Vocab("Filthy", "adj.", "骯髒的", "Enhanced"));
            initialVocabs.add(new Vocab("Sharp", "adj.", "鋒利的", "Enhanced"));
            initialVocabs.add(new Vocab("Dull", "adj.", "乏味的", "Enhanced"));
            initialVocabs.add(new Vocab("Intelligent", "adj.", "有才智的", "Enhanced"));
            initialVocabs.add(new Vocab("Brilliant", "adj.", "卓越的", "Enhanced"));
            initialVocabs.add(new Vocab("Wise", "adj.", "明智的", "Enhanced"));
            initialVocabs.add(new Vocab("Foolish", "adj.", "愚蠢的", "Enhanced"));
            initialVocabs.add(new Vocab("Generous", "adj.", "大方的", "Enhanced"));
            initialVocabs.add(new Vocab("Greedy", "adj.", "貪婪的", "Enhanced"));
            initialVocabs.add(new Vocab("Courteous", "adj.", "有禮貌的", "Enhanced"));
            initialVocabs.add(new Vocab("Hostile", "adj.", "有敵意的", "Enhanced"));
            //End of Enhanced
            // // // // // // // // // // // // //





            // Elite Mode
            initialVocabs.add(new Vocab("Abstract", "adj.", "抽象的", "Elite"));
            initialVocabs.add(new Vocab("Ambiguous", "adj.", "模棱兩可的", "Elite"));
            initialVocabs.add(new Vocab("Cognitive", "adj.", "認知的", "Elite"));
            initialVocabs.add(new Vocab("Diligent", "adj.", "勤奮的", "Elite"));
            initialVocabs.add(new Vocab("Eloquent", "adj.", "雄辯的", "Elite"));
            initialVocabs.add(new Vocab("Facilitate", "v.", "促進", "Elite"));
            initialVocabs.add(new Vocab("Generic", "adj.", "通用的", "Elite"));
            initialVocabs.add(new Vocab("Hypothesize", "v.", "假設", "Elite"));
            initialVocabs.add(new Vocab("Inevitable", "adj.", "不可避免的", "Elite"));
            initialVocabs.add(new Vocab("Justify", "v.", "證明...正當", "Elite"));
            initialVocabs.add(new Vocab("Kinship", "n.", "親屬關係", "Elite"));
            initialVocabs.add(new Vocab("Lucrative", "adj.", "有利可圖的", "Elite"));
            initialVocabs.add(new Vocab("Meticulous", "adj.", "嚴謹的", "Elite"));
            initialVocabs.add(new Vocab("Negligible", "adj.", "微不足道的", "Elite"));
            initialVocabs.add(new Vocab("Obsolete", "adj.", "過時的", "Elite"));
            initialVocabs.add(new Vocab("Pragmatic", "adj.", "務實的", "Elite"));
            initialVocabs.add(new Vocab("Quantitative", "adj.", "定量的", "Elite"));
            initialVocabs.add(new Vocab("Resilient", "adj.", "有韌性的", "Elite"));
            initialVocabs.add(new Vocab("Scrutinize", "v.", "審查", "Elite"));
            initialVocabs.add(new Vocab("Tangible", "adj.", "有形的", "Elite"));
            initialVocabs.add(new Vocab("Utilitarian", "adj.", "功利主義的", "Elite"));
            initialVocabs.add(new Vocab("Versatile", "adj.", "多才多藝的", "Elite"));
            initialVocabs.add(new Vocab("Widespread", "adj.", "廣泛的", "Elite"));
            initialVocabs.add(new Vocab("Yielding", "adj.", "易彎曲的", "Elite"));
            initialVocabs.add(new Vocab("Zeal", "n.", "熱情", "Elite"));
            initialVocabs.add(new Vocab("Aesthetic", "adj.", "美學的", "Elite"));
            initialVocabs.add(new Vocab("Benevolent", "adj.", "仁慈的", "Elite"));
            initialVocabs.add(new Vocab("Collaborate", "v.", "協作", "Elite"));
            initialVocabs.add(new Vocab("Decipher", "v.", "破譯", "Elite"));
            initialVocabs.add(new Vocab("Emphasize", "v.", "強調", "Elite"));
            initialVocabs.add(new Vocab("Formulate", "v.", "制定", "Elite"));
            initialVocabs.add(new Vocab("Gratify", "v.", "使滿足", "Elite"));
            initialVocabs.add(new Vocab("Hierarchy", "n.", "層級制度", "Elite"));
            initialVocabs.add(new Vocab("Implement", "v.", "實施", "Elite"));
            initialVocabs.add(new Vocab("Jurisdiction", "n.", "司法管轄權", "Elite"));
            initialVocabs.add(new Vocab("Kindle", "v.", "點燃 / 激起", "Elite"));
            initialVocabs.add(new Vocab("Legitimate", "adj.", "合法的", "Elite"));
            initialVocabs.add(new Vocab("Manifest", "v.", "表明", "Elite"));
            initialVocabs.add(new Vocab("Nominate", "v.", "提名", "Elite"));
            initialVocabs.add(new Vocab("Optimistic", "adj.", "樂觀的", "Elite"));
            initialVocabs.add(new Vocab("Paradox", "n.", "悖論", "Elite"));
            initialVocabs.add(new Vocab("Qualitative", "adj.", "定性的", "Elite"));
            initialVocabs.add(new Vocab("Rationalize", "v.", "使合理化", "Elite"));
            initialVocabs.add(new Vocab("Speculate", "v.", "推測", "Elite"));
            initialVocabs.add(new Vocab("Transient", "adj.", "短暫的", "Elite"));
            initialVocabs.add(new Vocab("Unanimous", "adj.", "全體一致的", "Elite"));
            initialVocabs.add(new Vocab("Validate", "v.", "驗證", "Elite"));
            initialVocabs.add(new Vocab("Whimsical", "adj.", "反覆無常的", "Elite"));
            initialVocabs.add(new Vocab("Xenophobia", "n.", "仇外心理", "Elite"));
            initialVocabs.add(new Vocab("Yearning", "n.", "渴望", "Elite"));
            initialVocabs.add(new Vocab("Zenith", "n.", "頂峰", "Elite"));
            initialVocabs.add(new Vocab("Alleviate", "v.", "減輕", "Elite"));
            initialVocabs.add(new Vocab("Belligerent", "adj.", "好戰的", "Elite"));
            initialVocabs.add(new Vocab("Complacent", "adj.", "自滿的", "Elite"));
            initialVocabs.add(new Vocab("Discrepancy", "n.", "差異", "Elite"));
            initialVocabs.add(new Vocab("Exacerbate", "v.", "使惡化", "Elite"));
            initialVocabs.add(new Vocab("Fabricate", "v.", "偽造", "Elite"));
            initialVocabs.add(new Vocab("Gregarious", "adj.", "愛社交的", "Elite"));
            initialVocabs.add(new Vocab("Holistic", "adj.", "整體的", "Elite"));
            initialVocabs.add(new Vocab("Impetus", "n.", "動力", "Elite"));
            initialVocabs.add(new Vocab("Judicious", "adj.", "明智的", "Elite"));
            initialVocabs.add(new Vocab("Lament", "v.", "哀悼", "Elite"));
            initialVocabs.add(new Vocab("Mitigate", "v.", "緩和", "Elite"));
            initialVocabs.add(new Vocab("Nostalgia", "n.", "懷舊", "Elite"));
            initialVocabs.add(new Vocab("Omnipotent", "adj.", "全能的", "Elite"));
            initialVocabs.add(new Vocab("Plausible", "adj.", "合理的", "Elite"));
            initialVocabs.add(new Vocab("Quaint", "adj.", "古色香的", "Elite"));
            initialVocabs.add(new Vocab("Rectify", "v.", "糾正", "Elite"));
            initialVocabs.add(new Vocab("Skeptical", "adj.", "懷疑的", "Elite"));
            initialVocabs.add(new Vocab("Ubiquitous", "adj.", "無所不在的", "Elite"));
            initialVocabs.add(new Vocab("Venerable", "adj.", "受尊敬的", "Elite"));
            initialVocabs.add(new Vocab("Warrant", "v.", "保證", "Elite"));
            initialVocabs.add(new Vocab("Yield", "n.", "產量", "Elite"));
            initialVocabs.add(new Vocab("Adhere", "v.", "堅持", "Elite"));
            initialVocabs.add(new Vocab("Coherent", "adj.", "連貫的", "Elite"));
            initialVocabs.add(new Vocab("Deviate", "v.", "偏離", "Elite"));
            initialVocabs.add(new Vocab("Elucidate", "v.", "闡明", "Elite"));
            initialVocabs.add(new Vocab("Finite", "adj.", "有限的", "Elite"));
            initialVocabs.add(new Vocab("Generate", "v.", "生成", "Elite"));
            initialVocabs.add(new Vocab("Hindrance", "n.", "阻礙", "Elite"));

            // --- Academic & Intellectual (35) ---
            initialVocabs.add(new Vocab("Aberration", "n.", "偏差 / 越軌", "Elite"));
            initialVocabs.add(new Vocab("Abstemious", "adj.", "有節制的", "Elite"));
            initialVocabs.add(new Vocab("Acquiesce", "v.", "默許", "Elite"));
            initialVocabs.add(new Vocab("Acrimony", "n.", "刻薄 / 尖酸", "Elite"));
            initialVocabs.add(new Vocab("Acumen", "n.", "敏銳 / 聰明", "Elite"));
            initialVocabs.add(new Vocab("Admonish", "v.", "告誡", "Elite"));
            initialVocabs.add(new Vocab("Adroit", "adj.", "靈巧的", "Elite"));
            initialVocabs.add(new Vocab("Adulation", "n.", "諂媚 / 奉承", "Elite"));
            initialVocabs.add(new Vocab("Alacrity", "n.", "欣然 / 敏捷", "Elite"));
            initialVocabs.add(new Vocab("Ambivalence", "n.", "矛盾心理", "Elite"));
            initialVocabs.add(new Vocab("Ameliorate", "v.", "改善 / 改進", "Elite"));
            initialVocabs.add(new Vocab("Anachronism", "n.", "時代錯誤", "Elite"));
            initialVocabs.add(new Vocab("Anomaly", "n.", "異常現象", "Elite"));
            initialVocabs.add(new Vocab("Antipathy", "n.", "反感 / 厭惡", "Elite"));
            initialVocabs.add(new Vocab("Apex", "n.", "頂點 / 巔峰", "Elite"));
            initialVocabs.add(new Vocab("Aphorism", "n.", "格言 / 警句", "Elite"));
            initialVocabs.add(new Vocab("Apocryphal", "adj.", "偽造的 / 虛假的", "Elite"));
            initialVocabs.add(new Vocab("Apprehension", "n.", "憂慮 / 逮捕", "Elite"));
            initialVocabs.add(new Vocab("Arcane", "adj.", "神秘的 / 晦澀的", "Elite"));
            initialVocabs.add(new Vocab("Arduous", "adj.", "艱巨的", "Elite"));
            initialVocabs.add(new Vocab("Ascetic", "adj.", "苦行的", "Elite"));
            initialVocabs.add(new Vocab("Assiduous", "adj.", "勤勉的", "Elite"));
            initialVocabs.add(new Vocab("Assuage", "v.", "緩和 / 減輕", "Elite"));
            initialVocabs.add(new Vocab("Astute", "adj.", "精明的", "Elite"));
            initialVocabs.add(new Vocab("Atrophy", "v.", "萎縮", "Elite"));
            initialVocabs.add(new Vocab("Audacious", "adj.", "大膽的", "Elite"));
            initialVocabs.add(new Vocab("Augment", "v.", "擴大 / 增加", "Elite"));
            initialVocabs.add(new Vocab("Austere", "adj.", "嚴厲的 / 簡樸的", "Elite"));
            initialVocabs.add(new Vocab("Autonomous", "adj.", "自治的", "Elite"));
            initialVocabs.add(new Vocab("Avarice", "n.", "貪婪", "Elite"));
            initialVocabs.add(new Vocab("Banal", "adj.", "平庸的 / 陳腐的", "Elite"));
            initialVocabs.add(new Vocab("Belie", "v.", "掩飾 / 證明...為假", "Elite"));
            initialVocabs.add(new Vocab("Benign", "adj.", "良性的 / 仁慈的", "Elite"));
            initialVocabs.add(new Vocab("Biased", "adj.", "有偏見的", "Elite"));
            initialVocabs.add(new Vocab("Bolster", "v.", "支持 / 加強", "Elite"));

// --- Logic & Rhetoric (35) ---
            initialVocabs.add(new Vocab("Bombastic", "adj.", "誇大的", "Elite"));
            initialVocabs.add(new Vocab("Burgeon", "v.", "迅速增長", "Elite"));
            initialVocabs.add(new Vocab("Buttress", "v.", "支撐 / 鞏固", "Elite"));
            initialVocabs.add(new Vocab("Cacophony", "n.", "雜音 / 刺耳的聲音", "Elite"));
            initialVocabs.add(new Vocab("Cajole", "v.", "哄騙", "Elite"));
            initialVocabs.add(new Vocab("Calumny", "n.", "誹謗 / 中傷", "Elite"));
            initialVocabs.add(new Vocab("Candid", "adj.", "坦率的", "Elite"));
            initialVocabs.add(new Vocab("Canon", "n.", "準則 / 正典", "Elite"));
            initialVocabs.add(new Vocab("Capricious", "adj.", "反覆無常的", "Elite"));
            initialVocabs.add(new Vocab("Castigate", "v.", "嚴厲責罵", "Elite"));
            initialVocabs.add(new Vocab("Catalyst", "n.", "催化劑 / 誘因", "Elite"));
            initialVocabs.add(new Vocab("Caustic", "adj.", "尖刻的 / 腐蝕性的", "Elite"));
            initialVocabs.add(new Vocab("Censure", "v.", "譴責", "Elite"));
            initialVocabs.add(new Vocab("Chicanery", "n.", "欺詐 / 詭計", "Elite"));
            initialVocabs.add(new Vocab("Coalesce", "v.", "合併 / 聯合", "Elite"));
            initialVocabs.add(new Vocab("Cogent", "adj.", "有說服力的", "Elite"));
            initialVocabs.add(new Vocab("Commensurate", "adj.", "相稱的", "Elite"));
            initialVocabs.add(new Vocab("Compelling", "adj.", "引人入勝的", "Elite"));
            initialVocabs.add(new Vocab("Comprehensive", "adj.", "全面的", "Elite"));
            initialVocabs.add(new Vocab("Conciliatory", "adj.", "和解的", "Elite"));
            initialVocabs.add(new Vocab("Condone", "v.", "寬容 / 饒恕", "Elite"));
            initialVocabs.add(new Vocab("Conducive", "adj.", "有助於...的", "Elite"));
            initialVocabs.add(new Vocab("Connoisseur", "n.", "鑒賞家", "Elite"));
            initialVocabs.add(new Vocab("Conscientious", "adj.", "認真的 / 盡責的", "Elite"));
            initialVocabs.add(new Vocab("Consensus", "n.", "共識", "Elite"));
            initialVocabs.add(new Vocab("Conspicuous", "adj.", "顯眼的", "Elite"));
            initialVocabs.add(new Vocab("Contentious", "adj.", "有爭議的", "Elite"));
            initialVocabs.add(new Vocab("Conundrum", "n.", "謎題 / 難題", "Elite"));
            initialVocabs.add(new Vocab("Convergent", "adj.", "趨同的", "Elite"));
            initialVocabs.add(new Vocab("Convoluted", "adj.", "複雜的 / 扭曲的", "Elite"));
            initialVocabs.add(new Vocab("Copious", "adj.", "豐富的", "Elite"));
            initialVocabs.add(new Vocab("Corroborate", "v.", "證實", "Elite"));
            initialVocabs.add(new Vocab("Credulous", "adj.", "輕信的", "Elite"));
            initialVocabs.add(new Vocab("Criterion", "n.", "標準", "Elite"));
            initialVocabs.add(new Vocab("Cryptic", "adj.", "隱晦的 / 神秘的", "Elite"));

// --- Social & Cultural (30) ---
            initialVocabs.add(new Vocab("Culpable", "adj.", "有罪的 / 應受譴責的", "Elite"));
            initialVocabs.add(new Vocab("Cynical", "adj.", "憤世嫉俗的", "Elite"));
            initialVocabs.add(new Vocab("Dearth", "n.", "缺乏 / 不足", "Elite"));
            initialVocabs.add(new Vocab("Debilitate", "v.", "使衰弱", "Elite"));
            initialVocabs.add(new Vocab("Decorum", "n.", "端莊 / 禮節", "Elite"));
            initialVocabs.add(new Vocab("Deference", "n.", "尊重 / 順從", "Elite"));
            initialVocabs.add(new Vocab("Deleterious", "adj.", "有害的", "Elite"));
            initialVocabs.add(new Vocab("Delineate", "v.", "描繪 / 輪廓", "Elite"));
            initialVocabs.add(new Vocab("Demur", "v.", "表示異議", "Elite"));
            initialVocabs.add(new Vocab("Denigrate", "v.", "抹黑 / 貶低", "Elite"));
            initialVocabs.add(new Vocab("Deride", "v.", "嘲笑", "Elite"));
            initialVocabs.add(new Vocab("Derivative", "adj.", "派生的 / 無創意的", "Elite"));
            initialVocabs.add(new Vocab("Desiccated", "adj.", "脫水的 / 枯燥的", "Elite"));
            initialVocabs.add(new Vocab("Desultory", "adj.", "散漫的 / 隨意的", "Elite"));
            initialVocabs.add(new Vocab("Detrimental", "adj.", "不利的 / 有害的", "Elite"));
            initialVocabs.add(new Vocab("Diatribe", "n.", "抨擊 / 惡言誹謗", "Elite"));
            initialVocabs.add(new Vocab("Didactic", "adj.", "說教的", "Elite"));
            initialVocabs.add(new Vocab("Diffident", "adj.", "缺乏自信的", "Elite"));
            initialVocabs.add(new Vocab("Digress", "v.", "離題", "Elite"));
            initialVocabs.add(new Vocab("Dilettante", "n.", "業餘愛好者", "Elite"));
            initialVocabs.add(new Vocab("Dirge", "n.", "輓歌", "Elite"));
            initialVocabs.add(new Vocab("Disabuse", "v.", "糾正 / 使醒悟", "Elite"));
            initialVocabs.add(new Vocab("Discern", "v.", "辨別 / 識別", "Elite"));
            initialVocabs.add(new Vocab("Discordant", "adj.", "不一致的 / 刺耳的", "Elite"));
            initialVocabs.add(new Vocab("Discreet", "adj.", "謹慎的", "Elite"));
            initialVocabs.add(new Vocab("Discrete", "adj.", "分離的 / 離散的", "Elite"));
            initialVocabs.add(new Vocab("Disingenuous", "adj.", "不誠實的 / 虛偽的", "Elite"));
            initialVocabs.add(new Vocab("Disinterested", "adj.", "公正的 / 無私心的", "Elite"));
            initialVocabs.add(new Vocab("Disparage", "v.", "輕視 / 貶低", "Elite"));
            initialVocabs.add(new Vocab("Disparate", "adj.", "迥然不同的", "Elite"));

// --- Professional & Technical (30) ---
            initialVocabs.add(new Vocab("Dissemble", "v.", "掩飾 / 假裝", "Elite"));
            initialVocabs.add(new Vocab("Disseminate", "v.", "傳播 / 散佈", "Elite"));
            initialVocabs.add(new Vocab("Dissolution", "n.", "瓦解 / 溶解", "Elite"));
            initialVocabs.add(new Vocab("Dissonance", "n.", "不和諧", "Elite"));
            initialVocabs.add(new Vocab("Distend", "v.", "膨脹", "Elite"));
            initialVocabs.add(new Vocab("Distill", "v.", "蒸餾 / 提取精華", "Elite"));
            initialVocabs.add(new Vocab("Divergent", "adj.", "分歧的", "Elite"));
            initialVocabs.add(new Vocab("Divest", "v.", "剝奪 / 脫衣", "Elite"));
            initialVocabs.add(new Vocab("Dogmatic", "adj.", "教條的 / 武斷的", "Elite"));
            initialVocabs.add(new Vocab("Dormant", "adj.", "休眠的", "Elite"));
            initialVocabs.add(new Vocab("Dubious", "adj.", "可疑的", "Elite"));
            initialVocabs.add(new Vocab("Duplicity", "n.", "口是心非 / 欺騙", "Elite"));
            initialVocabs.add(new Vocab("Ebullient", "adj.", "熱情洋溢的", "Elite"));
            initialVocabs.add(new Vocab("Eclectic", "adj.", "折衷的 / 兼收並蓄的", "Elite"));
            initialVocabs.add(new Vocab("Efficacy", "n.", "功效 / 效力", "Elite"));
            initialVocabs.add(new Vocab("Effrontery", "n.", "厚顏無恥", "Elite"));
            initialVocabs.add(new Vocab("Elegy", "n.", "哀歌", "Elite"));
            initialVocabs.add(new Vocab("Elicit", "v.", "引出 / 誘發", "Elite"));
            initialVocabs.add(new Vocab("Emancipate", "v.", "解放", "Elite"));
            initialVocabs.add(new Vocab("Embellish", "v.", "修飾 / 裝飾", "Elite"));
            initialVocabs.add(new Vocab("Empirical", "adj.", "經驗主義的", "Elite"));
            initialVocabs.add(new Vocab("Emulate", "v.", "效法 / 模仿", "Elite"));
            initialVocabs.add(new Vocab("Enervate", "v.", "使衰弱", "Elite"));
            initialVocabs.add(new Vocab("Engender", "v.", "產生 / 引起", "Elite"));
            initialVocabs.add(new Vocab("Enigma", "n.", "謎團", "Elite"));
            initialVocabs.add(new Vocab("Enumerate", "v.", "列舉", "Elite"));
            initialVocabs.add(new Vocab("Ephemeral", "adj.", "短暫的", "Elite"));
            initialVocabs.add(new Vocab("Equanimity", "n.", "鎮定 / 平靜", "Elite"));
            initialVocabs.add(new Vocab("Equivocate", "v.", "含糊其辭", "Elite"));
            initialVocabs.add(new Vocab("Erudite", "adj.", "博學的", "Elite"));

// --- Literature & Emotion (35) ---
            initialVocabs.add(new Vocab("Esoteric", "adj.", "深奧的 / 圈內人的", "Elite"));
            initialVocabs.add(new Vocab("Eulogy", "n.", "頌詞 / 悼詞", "Elite"));
            initialVocabs.add(new Vocab("Euphemism", "n.", "委婉語", "Elite"));
            initialVocabs.add(new Vocab("Exculpate", "v.", "開脫 / 宣告無罪", "Elite"));
            initialVocabs.add(new Vocab("Exigent", "adj.", "緊急的 / 迫切的", "Elite"));
            initialVocabs.add(new Vocab("Exonerate", "v.", "免除責任", "Elite"));
            initialVocabs.add(new Vocab("Explicit", "adj.", "明確的", "Elite"));
            initialVocabs.add(new Vocab("Exponent", "n.", "倡導者 / 指數", "Elite"));
            initialVocabs.add(new Vocab("Expunge", "v.", "擦除 / 刪除", "Elite"));
            initialVocabs.add(new Vocab("Extant", "adj.", "現存的", "Elite"));
            initialVocabs.add(new Vocab("Extemporaneous", "adj.", "即席的 / 無準備的", "Elite"));
            initialVocabs.add(new Vocab("Extrapolate", "v.", "推斷", "Elite"));
            initialVocabs.add(new Vocab("Facetious", "adj.", "滑稽的 / 亂開玩笑的", "Elite"));
            initialVocabs.add(new Vocab("Fallacious", "adj.", "謬誤的", "Elite"));
            initialVocabs.add(new Vocab("Fatuous", "adj.", "愚笨的", "Elite"));
            initialVocabs.add(new Vocab("Fawning", "adj.", "奉承的", "Elite"));
            initialVocabs.add(new Vocab("Felicitous", "adj.", "恰當的 / 幸福的", "Elite"));
            initialVocabs.add(new Vocab("Fervid", "adj.", "熾熱的", "Elite"));
            initialVocabs.add(new Vocab("Flag", "v.", "衰退 / 標記", "Elite"));
            initialVocabs.add(new Vocab("Fledgling", "n.", "幼鳥 / 新手", "Elite"));
            initialVocabs.add(new Vocab("Flout", "v.", "藐視 / 嘲弄", "Elite"));
            initialVocabs.add(new Vocab("Foment", "v.", "煽動 / 助長", "Elite"));
            initialVocabs.add(new Vocab("Forestall", "v.", "預先阻止", "Elite"));
            initialVocabs.add(new Vocab("Frugality", "n.", "節儉", "Elite"));
            initialVocabs.add(new Vocab("Futile", "adj.", "徒勞的", "Elite"));
            initialVocabs.add(new Vocab("Gainsay", "v.", "否認 / 反駁", "Elite"));
            initialVocabs.add(new Vocab("Garrulous", "adj.", "饒舌的", "Elite"));
            initialVocabs.add(new Vocab("Goad", "v.", "激勵 / 刺激", "Elite"));
            initialVocabs.add(new Vocab("Gouge", "v.", "欺詐 / 挖出", "Elite"));
            initialVocabs.add(new Vocab("Grandiloquent", "adj.", "誇張的", "Elite"));
            initialVocabs.add(new Vocab("Guile", "n.", "狡詐 / 欺騙", "Elite"));
            initialVocabs.add(new Vocab("Gullible", "adj.", "易受騙的", "Elite"));
            initialVocabs.add(new Vocab("Harangue", "n.", "慷慨激昂的演講", "Elite"));
            initialVocabs.add(new Vocab("Homogeneous", "adj.", "同質的", "Elite"));
            initialVocabs.add(new Vocab("Hyperbole", "n.", "誇張法", "Elite"));

// --- Complex Concepts (35) ---
            initialVocabs.add(new Vocab("Iconoclast", "n.", "打破舊習者", "Elite"));
            initialVocabs.add(new Vocab("Idolatry", "n.", "偶像崇拜", "Elite"));
            initialVocabs.add(new Vocab("Immutable", "adj.", "不變的", "Elite"));
            initialVocabs.add(new Vocab("Impair", "v.", "損害 / 削弱", "Elite"));
            initialVocabs.add(new Vocab("Impassive", "adj.", "冷漠的 / 無動於衷的", "Elite"));
            initialVocabs.add(new Vocab("Impecunious", "adj.", "貧困的", "Elite"));
            initialVocabs.add(new Vocab("Impede", "v.", "阻礙", "Elite"));
            initialVocabs.add(new Vocab("Impermeable", "adj.", "不滲透的", "Elite"));
            initialVocabs.add(new Vocab("Imperturbable", "adj.", "冷靜的", "Elite"));
            initialVocabs.add(new Vocab("Impervious", "adj.", "不受影響的", "Elite"));
            initialVocabs.add(new Vocab("Implacable", "adj.", "難以安撫的", "Elite"));
            initialVocabs.add(new Vocab("Implicit", "adj.", "含蓄的 / 絕對的", "Elite"));
            initialVocabs.add(new Vocab("Implode", "v.", "向內塌陷", "Elite"));
            initialVocabs.add(new Vocab("Inadvertently", "adv.", "不經意地", "Elite"));
            initialVocabs.add(new Vocab("Inchoate", "adj.", "初步的 / 不完全的", "Elite"));
            initialVocabs.add(new Vocab("Incongruity", "n.", "不協調", "Elite"));
            initialVocabs.add(new Vocab("Inconsequential", "adj.", "無關緊要的", "Elite"));
            initialVocabs.add(new Vocab("Incorporate", "v.", "合併 / 包含", "Elite"));
            initialVocabs.add(new Vocab("Indeterminacy", "n.", "不確定性", "Elite"));
            initialVocabs.add(new Vocab("Indigence", "n.", "貧困", "Elite"));
            initialVocabs.add(new Vocab("Indolent", "adj.", "懶惰的", "Elite"));
            initialVocabs.add(new Vocab("Inert", "adj.", "惰性的", "Elite"));
            initialVocabs.add(new Vocab("Ingenuous", "adj.", "天真的 / 率直的", "Elite"));
            initialVocabs.add(new Vocab("Inherent", "adj.", "固有的", "Elite"));
            initialVocabs.add(new Vocab("Innocuous", "adj.", "無害的", "Elite"));
            initialVocabs.add(new Vocab("Insensible", "adj.", "無感覺的", "Elite"));
            initialVocabs.add(new Vocab("Insinuate", "v.", "暗示 / 影射", "Elite"));
            initialVocabs.add(new Vocab("Insipid", "adj.", "平淡無味的", "Elite"));
            initialVocabs.add(new Vocab("Insularity", "n.", "與世隔絕 / 偏狹", "Elite"));
            initialVocabs.add(new Vocab("Intractable", "adj.", "難處理的 / 倔強的", "Elite"));
            initialVocabs.add(new Vocab("Intransigence", "n.", "不妥協", "Elite"));
            initialVocabs.add(new Vocab("Inundate", "v.", "淹沒 / 氾濫", "Elite"));
            initialVocabs.add(new Vocab("Inured", "adj.", "習慣了的", "Elite"));
            initialVocabs.add(new Vocab("Invective", "n.", "漫罵 / 惡言", "Elite"));
            initialVocabs.add(new Vocab("Irascible", "adj.", "易怒的", "Elite"));
            //End of Basic
            // // // // // // // // // // // // //






            vocabDao.insertAll(initialVocabs);
        }
    }
}