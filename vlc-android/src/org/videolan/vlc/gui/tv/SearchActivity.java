/*****************************************************************************
 * SearchActivity.java
 *****************************************************************************
 * Copyright © 2014-2015 VLC authors, VideoLAN and VideoLabs
 * Author: Geoffrey Métais
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/
package org.videolan.vlc.gui.tv;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;

import org.videolan.vlc.R;
import org.videolan.vlc.gui.BaseActivity;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class SearchActivity extends BaseActivity {

    SearchFragment mFragment;
    private static final int REQUEST_SPEECH = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_search);

        mFragment = (SearchFragment) getFragmentManager()
                .findFragmentById(R.id.search_fragment);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction()) || "com.google.android.gms.actions.SEARCH_ACTION".equals(intent.getAction())) {
            mFragment.onQueryTextSubmit(intent.getStringExtra(SearchManager.QUERY));
        } else {
            SpeechRecognitionCallback speechRecognitionCallback = new SpeechRecognitionCallback() {

                @Override
                public void recognizeSpeech() {
                    startActivityForResult(mFragment.getRecognizerIntent(), REQUEST_SPEECH);
                }
            };
            mFragment.setSpeechRecognitionCallback(speechRecognitionCallback);
        }
    }

    @Override
    public boolean onSearchRequested() {
        mFragment.startRecognition();
        return true;
    }
}
