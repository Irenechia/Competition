package com.example.competition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    public String now,day ;
    int weekInt;
    private String week[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private String words[] = {"你若遇上麻烦，不要逞强，你就跑，远远跑开","掉在水里你不会淹死，呆在水里你才会淹死，你只有游，不停的往前游",
            "生活就像一颗巧克力，你永远都不会知道下一颗的滋味","时间可以吞噬一切，但它丝毫不能减少的是你伟大的思想，你的幽默，你的善良，还有你的勇气",
            "如果你不出去走走，你就会以为这就是世界","在我的生命中有你是多么幸运，当我回忆过去，眼前就会浮现你的脸庞，你总会在那守候着我",
            "有人就有恩怨，有恩怨就有江湖。人就是江湖，你怎么退出","改变，人人都会有的。当你真正面对改变，你也就觉得坦然了","刻意伤害别人不可原谅，我从未这样做，也无需因此而感到愧疚",
            "人世间的事情莫过于此，用一个瞬间来喜欢一样东西，然后用多年的时间来慢慢拷问自己为什么会喜欢这样东西","人生本就是苦还是只有童年苦？生命就是如此",
            "星星在哪里都是很亮的，就看你有没有抬头去看他们","旧梦很美，虽未能实现，但我很欣慰它们曾萦绕心田","有些人能清楚地听到自己内心深处的声音，并以此行事。这些人要么变成了疯子，要么成为传奇",
            "人活这一世，能耐还在其次。有的成了面子，有的成了里子，都是时势使然","决定我们成为什么样人的，不是我们的能力，而是我们的选择",
            "你每天都在做很多看起来毫无意义的决定，但某天你的某个决定就能改变你的一生","好好活就是干有意义的事，有意义的事就是好好活",
            "生下来的时候都只有一半，为了找到另一半而在人世间行走。有的人幸运，很快就找到了。而有人却要找一辈子","当黑暗与光明交会，我们都需要一些勇气",
            "一个人只能全力以赴，等待命运去揭晓答案","如果额头终将刻上皱纹，你只能做到皱纹不要刻在你心上",
            "生活中有很多的不如意。如果一不开心，就寄希望于如果当初，那你永远都不会开心。幻景再美终是梦，珍惜眼前始为真",
            "手不是用来打人的，而是用来拥抱你所爱的人；脚不是用来踢人的，是用来向理想的目标迈进的","有信心不一定会成功，没信心一定不会成功",
            "会说谎骗人，只想着保全自己，是因为已经从孩子长成了大人","纷乱人世间，除了你一切繁华都是背景。这出戏用生命演下去，付出的青春不可惜",
            "我不觉得我是对的，但我也觉得不是错的","遇事要泰然处之，得意时要淡然，失意时要坦然。在人之上，要把人当人看；在人之下，要把自己当人看",
            "紧要关头不放弃，绝望就会变成希望","我现在才知道，他能够开开心心在外面走来走去的，是因为他知道始终有着一个地方等着他",
            "人，归根结底，是一个物质存在，很容易受损伤，却不容易修复","这世上的每件东西都是有用的，即使是这块石头","如果这个世界对你不理不睬，你也可以这样对待它"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int radom = (int)(Math.random()*30+1);

        final Date today = Calendar.getInstance().getTime();
        weekInt = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
        now = sdf.format(today);
        day = sdf2.format(today);
        Log.i(TAG, "onCreate: "+now);
        Log.i(TAG, "onCreate: "+day);
        TextView tvDate = findViewById(R.id.textView);
        TextView tvDay = findViewById(R.id.textView2);
        TextView tvWord = findViewById(R.id.textView3);
        TextView tvWeek = findViewById(R.id.textView4);
        tvDate.setText(now);
        tvDay.setText(day);
        Log.i(TAG, "onCreate: "+week[weekInt]);
        tvWeek.setText(week[weekInt]);
        tvWord.setText(words[radom]);

    }



    public void openLogin(View btn){
        Log.i(TAG, "openOne:open ");
        Intent toMy = new Intent(this,LoginActivity.class);
        startActivity(toMy);
    }
}