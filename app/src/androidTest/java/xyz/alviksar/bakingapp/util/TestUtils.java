package xyz.alviksar.bakingapp.util;

import com.google.gson.Gson;

import xyz.alviksar.bakingapp.model.Recipe;

public class TestUtils {
    private final static String RECIPE_JSON = "  {\n" +
            "    \"id\": 4,\n" +
            "    \"name\": \"Cheesecake\",\n" +
            "    \"ingredients\": [\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"CUP\",\n" +
            "        \"ingredient\": \"Graham Cracker crumbs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 6,\n" +
            "        \"measure\": \"TBLSP\",\n" +
            "        \"ingredient\": \"unsalted butter, melted\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 250,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"granulated sugar\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 1,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"salt\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 4,\n" +
            "        \"measure\": \"TSP\",\n" +
            "        \"ingredient\": \"vanilla,divided\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 680,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"cream cheese, softened\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 3,\n" +
            "        \"measure\": \"UNIT\",\n" +
            "        \"ingredient\": \"large whole eggs\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 2,\n" +
            "        \"measure\": \"UNIT\",\n" +
            "        \"ingredient\": \"large egg yolks\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"quantity\": 250,\n" +
            "        \"measure\": \"G\",\n" +
            "        \"ingredient\": \"heavy cream\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"steps\": [\n" +
            "      {\n" +
            "        \"id\": 0,\n" +
            "        \"shortDescription\": \"Recipe Introduction\",\n" +
            "        \"description\": \"Recipe Introduction\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdae8_-intro-cheesecake/-intro-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 1,\n" +
            "        \"shortDescription\": \"Starting prep.\",\n" +
            "        \"description\": \"1. Preheat the oven to 350\\u00b0F. Grease the bottom of a 9-inch round springform pan with butter. \",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 2,\n" +
            "        \"shortDescription\": \"Prep the cookie crust.\",\n" +
            "        \"description\": \"2. To assemble the crust, whisk together the cookie crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt for the crust in a medium bowl. Stir in the melted butter and 1 teaspoon of vanilla extract until uniform. \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb1d_2-form-crust-to-bottom-of-pan-cheesecake/2-form-crust-to-bottom-of-pan-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 3,\n" +
            "        \"shortDescription\": \"Start water bath.\",\n" +
            "        \"description\": \"3. Fill a large roasting pan with a few inches of hot water and place it on the bottom rack of the oven.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 4,\n" +
            "        \"shortDescription\": \"Prebake cookie crust. \",\n" +
            "        \"description\": \"4. Press the cookie mixture into the bottom and slightly up the sides of the prepared pan. Bake for 11 minutes and then let cool.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5,\n" +
            "        \"shortDescription\": \"Mix cream cheese and dry ingredients.\",\n" +
            "        \"description\": \"5. Beat the cream cheese, remaining 200 grams (1 cup) of sugar, and remaining 1/2 teaspoon salt on medium speed in a stand mixer with the paddle attachment for 3 minutes (or high speed if using a hand mixer). \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb3a_3-mix-sugar-salt-together-cheesecake/3-mix-sugar-salt-together-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 6,\n" +
            "        \"shortDescription\": \"Add eggs.\",\n" +
            "        \"description\": \"6. Scrape down the sides of the pan. Add in the eggs one at a time, beating each one on medium-low speed just until incorporated. Scrape down the sides and bottom of the bowl. Add in both egg yolks and beat until just incorporated. \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb55_4-add-eggs-mix-cheesecake/4-add-eggs-mix-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 7,\n" +
            "        \"shortDescription\": \"Add heavy cream and vanilla.\",\n" +
            "        \"description\": \"7. Add the cream and remaining tablespoon of vanilla to the batter and beat on medium-low speed until just incorporated. \",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb72_5-mix-vanilla-cream-together-cheesecake/5-mix-vanilla-cream-together-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 8,\n" +
            "        \"shortDescription\": \"Pour batter in pan.\",\n" +
            "        \"description\": \"8. Pour the batter into the cooled cookie crust. Bang the pan on a counter or sturdy table a few times to release air bubbles from the batter.\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb88_6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake/6-add-the-batter-to-the-pan-w-the-crumbs-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 9,\n" +
            "        \"shortDescription\": \"Bake the cheesecake.\",\n" +
            "        \"description\": \"9. Bake the cheesecake on a middle rack of the oven above the roasting pan full of water for 50 minutes. \",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 10,\n" +
            "        \"shortDescription\": \"Turn off oven and leave cake in.\",\n" +
            "        \"description\": \"10. Turn off the oven but keep the cheesecake in the oven with the door closed for 50 more minutes.\",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 11,\n" +
            "        \"shortDescription\": \"Remove from oven and cool at room temperature.\",\n" +
            "        \"description\": \"11. Take the cheesecake out of the oven. It should look pale yellow or golden on top and be set but still slightly jiggly. Let it cool to room temperature. \",\n" +
            "        \"videoURL\": \"\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 12,\n" +
            "        \"shortDescription\": \"Final cooling and set.\",\n" +
            "        \"description\": \"12. Cover the cheesecake with plastic wrap, not allowing the plastic to touch the top of the cake, and refrigerate it for at least 8 hours. Then it's ready to serve!\",\n" +
            "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdbac_9-finished-product-cheesecake/9-finished-product-cheesecake.mp4\",\n" +
            "        \"thumbnailURL\": \"\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"servings\": 8,\n" +
            "    \"image\": \"\"\n" +
            "  }";


    public static Recipe getTestRecipe() {
        return new Gson().fromJson(RECIPE_JSON, Recipe.class);
   }

}
