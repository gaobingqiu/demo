package base;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.ChatFragment;
import com.example.demo.ContactsFragment;
import com.example.demo.FriendFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	List<Fragment> fragmentList = new ArrayList<Fragment>();

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			ChatFragment chatFragment = new ChatFragment();
			return chatFragment;
		case 1:
			ContactsFragment contactsFragment = new ContactsFragment();
			return contactsFragment;
		case 2:
			FriendFragment friendFragment = new FriendFragment();
			return friendFragment;
		default:
			return null;
		}

	}

	@Override
	public int getCount() {
		return 3;
	}
	
}
