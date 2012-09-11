package com.hy;

import meishi.ui.LogoActivity;
import meishi.ui.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LogoActivityTest {
	@Test
    public void shouldHaveHappySmiles() throws Exception {
        String hello = new LogoActivity().getResources().getString(R.string.hello);
        assertThat(hello, equalTo("Hello World, MeishiActivity!"));
    }
}
