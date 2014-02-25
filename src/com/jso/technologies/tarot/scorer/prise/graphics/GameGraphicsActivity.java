package com.jso.technologies.tarot.scorer.prise.graphics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jso.technologies.tarot.scorer.R;
import com.jso.technologies.tarot.scorer.Utils.Constantes;
import com.jso.technologies.tarot.scorer.common.bean.Game;

public class GameGraphicsActivity  extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_graphics);
		
		Intent passedIntent = getIntent();
		Game game = (Game) passedIntent.getExtras().get(Constantes.GAME);
		game.fillPlayersIfNeeded(this);
		
		PriseGraphicsPageAdapter adapter = new PriseGraphicsPageAdapter(this, game);
	    ViewPager graphicsViewPager = (ViewPager) findViewById(R.id.gameGraphicsViewPager);
	    graphicsViewPager.setAdapter(adapter);
	    graphicsViewPager.setCurrentItem(0);
	}

}
