package com.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {

    private ListView mList;
    private List<Item> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mList = (ListView) findViewById(R.id.list);
        mItems = getXmlContent("http://blog.developpez.com/xmlsrv/rss2.php?blog=389");

        Log.v("trololo", "mItems size : " + mItems.size());

        CustomAdapter myAdapter = new CustomAdapter(this, mItems);
        mList.setAdapter(myAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(mItems.get(i).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private List<Item> getXmlContent(String _url) {
        List<Item> currentItems = new ArrayList<Item>();

        try {
            URL url = new URL(_url);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(getInputStream(url), "UTF_8");
            int eventType = xpp.getEventType();
            Item currentItem = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (xpp.getName() != null && xpp.getName().equalsIgnoreCase("item")) {
                    if (eventType == XmlPullParser.END_TAG) {
                        currentItems.add(currentItem);
                        currentItem = null;
                    } else currentItem = new Item();
                } else if (xpp.getName() != null && xpp.getName().equalsIgnoreCase("title")) {
                    if (currentItem != null) {
                        String title = xpp.nextText();
                        Log.v("trololo", "title : " + title);
                        currentItem.setHeadline(title);
                    }
                } else if (xpp.getName() != null && xpp.getName().equalsIgnoreCase("link")) {
                    if (currentItem != null)
                        currentItem.setLink(xpp.nextText());
                } else if (xpp.getName() != null && xpp.getName().equalsIgnoreCase("description")) {
                    if (currentItem != null)
                        currentItem.setDescription(xpp.nextText());
                }
                  eventType = xpp.next();
            }
        } catch (
                MalformedURLException e
                )

        {
            e.printStackTrace();
        } catch (
                XmlPullParserException e
                )

        {
            e.printStackTrace();
        } catch (
                IOException e
                )

        {
            e.printStackTrace();
        }

        return currentItems;
    }


    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
