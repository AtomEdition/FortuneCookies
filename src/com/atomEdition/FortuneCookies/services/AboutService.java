package com.atomEdition.FortuneCookies.services;

import android.content.Context;
import android.content.ContextWrapper;
import com.atomEdition.FortuneCookies.R;
import com.atomEdition.FortuneCookies.Utils;

/**
 * Created by FruityDevil on 02.09.2015.
 */
public class AboutService extends ContextWrapper {

    public AboutService(Context baseContext) {
        super(baseContext);
    }

    public String getAboutTextByCategory(Integer category) {

        int aboutTextId = getResources()
                .getIdentifier(Utils.ABOUT_TEXT_STRING_IDENTIFIER + category, "string", getPackageName());

        int stringArrayCategory = getResources()
                .getIdentifier(Utils.PROPHECY_CATEGORY_NAME + category, "array", getPackageName());
        int propheciesCount = (category == 0)
                ? Utils.PROPHECY_COUNT_TOTAL
                : getResources().getStringArray(stringArrayCategory).length;
        StringBuilder stringBuilder = new StringBuilder()
                .append(getString(aboutTextId))
                .append("\n")
                .append(Utils.PROPHECY_ARRAY_WAS_ADDED_IN_LAST_PATCH[category])
                .append(" ")
                .append(getString(R.string.about_prophecies_added))
                .append(", ")
                .append(propheciesCount)
                .append(" ")
                .append(getString(R.string.about_prophecies_total));
        return stringBuilder.toString();

    }

}
