package productivetime.domain;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class offers a method to write activities to a CSV-file.
 */
public class CSVExport {

    /**
     * Exports the given activities as a .csv-file.
     * @param activities list of activities.
     * @param file a file for the activities to be saved in.
     * @return true if the export is successful, otherwise false.
     */
    public static boolean writeActivitiesToCSV(List<Activity> activities, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            CSVWriter csvWriter = new CSVWriter(fileWriter);

            csvWriter.writeNext(new String[]{"Id", "Type", "Start (Unix time, s)", "Duration (s)"});

            for (Activity activity : activities) {
                String[] activityString = {String.valueOf(activity.getId()), activity.getType(), String.valueOf(activity.getStart()), null};
                if (activity.isOngoing()) {
                    activityString[3] = "ongoing";
                } else {
                    activityString[3] = String.valueOf(activity.getDuration());
                }
                csvWriter.writeNext(activityString);
            }

            csvWriter.close();

            return true;

        } catch (IOException e) {
            return false;
        }
    }

}
