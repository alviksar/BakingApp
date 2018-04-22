package xyz.alviksar.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import xyz.alviksar.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BakingAppWidgetConfigureActivity BakingAppWidgetConfigureActivity}
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //    CharSequence widgetText = BakingAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        Recipe recipe = BakingAppWidgetConfigureActivity.loadRecipePref(context, appWidgetId);
        CharSequence widgetText;
        if (recipe != null)
            widgetText = recipe.getIngredientsString();
        else
            widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, StepListActivity.class);
        // Make the pending intent unique
        // https://stackoverflow.com/a/5158408/9682456
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);  // Identifies the particular widget...
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        intent.putExtra(Recipe.PARCEBLE_NAME, recipe);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent pendIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.appwidget_text, pendIntent);

//        Intent stepListIntent = new Intent(context, StepListActivity.class);
//        stepListIntent.putExtra(Recipe.PARCEBLE_NAME, recipe);
//// Create the TaskStackBuilder and add the intent, which inflates the back stack
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addNextIntentWithParentStack(stepListIntent);
//// Get the PendingIntent containing the entire back stack
//        PendingIntent stepListPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

//        // Perform this loop procedure for each App Widget that belongs to this provider
//        for (int appWidgetId : appWidgetIds) {
//
////            // Create an Intent to launch ExampleActivity
//            Intent intent = new Intent(context, MainActivity.class);
//            Recipe recipe = BakingAppWidgetConfigureActivity.loadRecipePref(context, appWidgetId);
//            intent.putExtra(Recipe.PARCEBLE_NAME, recipe);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
////
//            // Create an Intent for the activity you want to start
//            Intent stepListIntent = new Intent(context, StepListActivity.class);
//            Recipe recipe = BakingAppWidgetConfigureActivity.loadRecipePref(context, appWidgetId);
//            stepListIntent.putExtra(Recipe.PARCEBLE_NAME, recipe);
//// Create the TaskStackBuilder and add the intent, which inflates the back stack
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//            stackBuilder.addNextIntentWithParentStack(stepListIntent);
//// Get the PendingIntent containing the entire back stack
//            PendingIntent stepListPendingIntent =
//                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

//
//            // Get the layout for the App Widget and attach an on-click listener
//            // to the button
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
//            views.setOnClickPendingIntent(R.id.appwidget_text, stepListPendingIntent);
//
//            // Tell the AppWidgetManager to perform an update on the current app widget
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            //           BakingAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
            BakingAppWidgetConfigureActivity.deleteRecipePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

