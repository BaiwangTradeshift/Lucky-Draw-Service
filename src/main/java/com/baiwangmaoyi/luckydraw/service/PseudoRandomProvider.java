package com.baiwangmaoyi.luckydraw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
public class PseudoRandomProvider implements RandomProvider {

    public <T> List<T> pickRandomly(List<T> sourceList, int itemsToSelect) {
        if (sourceList == null || sourceList.size() == 0) {
            throw new RuntimeException("Source list can't be empty");
        }
        Random random = new Random();
        int sourceSize = sourceList.size();

        // Generate an array representing the element to select from 0... number of available
        // elements after previous elements have been selected.
        int[] selections = new int[itemsToSelect];

        // Simultaneously use the select indices table to generate the new result array
        ArrayList<T> resultArray = new ArrayList<T>();

        for (int count = 0; count < itemsToSelect; count++) {

            // An element from the elements *not yet chosen* is selected
            int selection = random.nextInt(sourceSize - count);
            selections[count] = selection;
            // Store original selection in the original range 0.. number of available elements

            // This selection is converted into actual array space by iterating through the elements
            // already chosen.
            for (int scanIdx = count - 1; scanIdx >= 0; scanIdx--) {
                if (selection >= selections[scanIdx]) {
                    selection++;
                }
            }
            // When the first selected element record is reached all selections are in the range
            // 0.. number of available elements, and free of collisions with previous entries.

            // Write the actual array entry to the results
            resultArray.add(sourceList.get(selection));
        }
        return resultArray;
    }
}
