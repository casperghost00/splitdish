package com.splitdish.pos.menu;

import java.util.HashSet;
import java.util.TreeSet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.splitdish.lib.MenuItem;
import com.splitdish.lib.MenuItemList;
import com.splitdish.pos.R;

public class MenuPageFragment extends Fragment {
	
	public static final String ARGS_CATEGORY_TITLE = "com.splitdish.pos.menu.ARGS_CATEGORY_TITLE";
	
	private MenuItemList mMenuItemList = null;
	private String mCategory = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		mCategory = args.getString(ARGS_CATEGORY_TITLE);
        
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	

		mMenuItemList = GlobalMenu.getGlobalMenu().getListByCategory(mCategory);
    	
    	View menuView = inflater.inflate(R.layout.menu_page_fragment, container, false);
    	

		addLettersToMenu(menuView);
    	
    	return menuView;
    }
    

	private void addLettersToMenu(View menuView) {

		ImageView letterView = null;
		GridLayout.LayoutParams params = null;
		//TODO Fix this
		GridLayout grid = (GridLayout)menuView.findViewById(R.id.menu_grid_layout);
		HashSet<Character> charHash = new HashSet<Character>();
		for(MenuItem item : mMenuItemList) {
			char firstChar = item.getName().charAt(0);
			firstChar = Character.toLowerCase(firstChar);
			
			charHash.add(firstChar);
		}
		
		TreeSet<Character> sortedChars = new TreeSet<Character>(charHash);
		
		for(Character c : sortedChars) {
			
			letterView = new ImageView(getActivity());
			params = new GridLayout.LayoutParams();
			
			letterView.setImageResource(letterToResource(c));
			
			params.width = 150;
			params.height = 150;
			letterView.setPadding(15, 15, 15, 15);
			
			letterView.setLayoutParams(params);
			grid.addView(letterView);
		}
	}
	
	private int letterToResource(char letter) {
		
		switch(letter) {
		case 'a': return R.drawable.letter_a;
		case 'b': return R.drawable.letter_b;
		case 'c': return R.drawable.letter_c;
		case 'd': return R.drawable.letter_d;
		case 'e': return R.drawable.letter_e;
		case 'f': return R.drawable.letter_f;
		case 'g': return R.drawable.letter_g;
		case 'h': return R.drawable.letter_h;
		case 'i': return R.drawable.letter_i;
		case 'j': return R.drawable.letter_j;
		case 'k': return R.drawable.letter_k;
		case 'l': return R.drawable.letter_l;
		case 'm': return R.drawable.letter_m;
		case 'n': return R.drawable.letter_n;
		case 'o': return R.drawable.letter_o;
		case 'p': return R.drawable.letter_p;
		case 'q': return R.drawable.letter_q;
		case 'r': return R.drawable.letter_r;
		case 's': return R.drawable.letter_s;
		case 't': return R.drawable.letter_t;
		case 'u': return R.drawable.letter_u;
		case 'v': return R.drawable.letter_v;
		case 'w': return R.drawable.letter_w;
		case 'x': return R.drawable.letter_x;
		case 'y': return R.drawable.letter_y;
		case 'z': return R.drawable.letter_z;
		}		
		return 0;
	}
}
