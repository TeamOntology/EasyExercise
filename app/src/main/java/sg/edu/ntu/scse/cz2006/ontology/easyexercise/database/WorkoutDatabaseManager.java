package sg.edu.ntu.scse.cz2006.ontology.easyexercise.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.CustomizedLocation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.PrivateWorkoutPlan;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.PublicWorkoutPlan;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.WorkoutRecord;

/**
 * TODO: Add Javadoc
 */
public class WorkoutDatabaseManager {
    public static PrivateWorkoutPlan toWorkoutPlan(FirebasePrivateWorkoutPlan firebasePlan, Context context) {
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        Sport s = manager.getSportById(firebasePlan.getSportID());
        Location l;
        if (firebasePlan.isCustomized()) {
            Coordinates c = new Coordinates(firebasePlan.getLatitude(), firebasePlan.getLongitude(), firebasePlan.getName());
            l = new CustomizedLocation(c);
        } else {
            l = manager.getFacilityById(firebasePlan.getFacilityID());
        }
        manager.closeDatabase();
        return new PrivateWorkoutPlan(s, l, firebasePlan.getPlanID());
    }

    public static PublicWorkoutPlan toPublicPlan(FirebasePublicWorkoutPlan firebasePlan, Context context) {
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        Sport s = manager.getSportById(firebasePlan.getSportID());
        Location l = manager.getFacilityById(firebasePlan.getFacilityID());
        manager.closeDatabase();
        return new PublicWorkoutPlan(
                s, l, firebasePlan.getPlanID(), firebasePlan.getPlanLimit(),
                new Date(firebasePlan.getPlanStart()), new Date(firebasePlan.getPlanFinish()),
                firebasePlan.getMembers());
    }

    public static WorkoutRecord toWorkoutRecord(FirebaseWorkoutRecord firebaseRecord, Context context) {
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        Sport s = manager.getSportById(firebaseRecord.getSportID());
        Location l;
        if (firebaseRecord.isCustomized()) {
            Coordinates c = new Coordinates(firebaseRecord.getLatitude(),
                    firebaseRecord.getLongitude(),
                    firebaseRecord.getName());
            l = new CustomizedLocation(c);
        } else {
            l = manager.getFacilityById(firebaseRecord.getFacilityID());
        }
        manager.closeDatabase();
        return new WorkoutRecord(
                s, l, firebaseRecord.getPlanID(),
                new Date(firebaseRecord.getStartTime()),
                new Date(firebaseRecord.getEndTime()),
                firebaseRecord.getDuration());
    }

    public static class FirebaseWorkoutRecord {
        private long sportID;
        private String planID;
        private boolean customized;
        private long facilityID;
        private double longitude;
        private double latitude;
        private long startTime;
        private long endTime;
        private String duration;
        private String name;

        public FirebaseWorkoutRecord() {
        }

        public FirebaseWorkoutRecord(WorkoutRecord record) {
            sportID = record.getSport().getId();
            planID = record.getPlanID();
            customized = (record.getLocation().getType() == Location.LocationType.CUSTOMISED_LOCATION);
            if (customized) {
                longitude = record.getLocation().getLongitude();
                latitude = record.getLocation().getLatitude();
                name = record.getLocation().getName();
                facilityID = -1;
            } else {
                longitude = -1;
                latitude = -1;
                name = "";
                facilityID = ((Facility) record.getLocation()).getId();
            }
            duration = record.getDuration();
            startTime = record.getStartTime().getTime();
            endTime = record.getEndTime().getTime();
        }

        public long getSportID() {
            return sportID;
        }

        public String getPlanID() {
            return planID;
        }

        public boolean isCustomized() {
            return customized;
        }

        public long getFacilityID() {
            return facilityID;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public String getName() {
            return name;
        }

        public String getDuration() {
            return duration;
        }
    }

    public static class FirebasePublicWorkoutPlan {
        private int planLimit;
        private long planStart;
        private long planFinish;
        private String planID;
        private long sportID;
        private long facilityID;
        private List<String> members;

        public FirebasePublicWorkoutPlan() {
        }

        public FirebasePublicWorkoutPlan(
                int planLimit,
                long planStart,
                long planFinish,
                String planID,
                long sportID,
                long facilityID,
                List<String> members) {
            this.planLimit = planLimit;
            this.planStart = planStart;
            this.planFinish = planFinish;
            this.planID = planID;
            this.sportID = sportID;
            this.facilityID = facilityID;
            this.members = members;
        }

        public FirebasePublicWorkoutPlan(
                int planLimit,
                Date planStart,
                Date planFinish,
                String planID,
                long sportID,
                long facilityID,
                String userID) {
            this.planLimit = planLimit;
            this.planStart = planStart.getTime();
            this.planFinish = planFinish.getTime();
            this.planID = planID;
            this.sportID = sportID;
            this.facilityID = facilityID;
            members = new ArrayList<>();
            members.add(userID);
        }

        public int getPlanLimit() {
            return planLimit;
        }

        public long getPlanStart() {
            return planStart;
        }

        public long getPlanFinish() {
            return planFinish;
        }

        public String getPlanID() {
            return planID;
        }

        public long getSportID() {
            return sportID;
        }

        public long getFacilityID() {
            return facilityID;
        }

        public List<String> getMembers() {
            return members;
        }

        public void addMember(String id) {
            this.members.add(id);
        }

        public void removeMember(int i) {
            this.members.remove(i);
        }
    }


    public static class FirebasePrivateWorkoutPlan {
        private long sportID;
        private String planID;
        private boolean customized;
        private long facilityID;
        private double longitude;
        private double latitude;
        private String name;

        public FirebasePrivateWorkoutPlan() {
        }

        public FirebasePrivateWorkoutPlan(PrivateWorkoutPlan plan, String id) {
            sportID = plan.getSport().getId();
            planID = id;
            customized = (plan.getLocation().getType() == Location.LocationType.CUSTOMISED_LOCATION);
            if (customized) {
                longitude = plan.getLocation().getLongitude();
                latitude = plan.getLocation().getLatitude();
                name = plan.getLocation().getName();
                facilityID = -1;
            } else {
                longitude = -1;
                latitude = -1;
                name = "";
                facilityID = ((Facility) plan.getLocation()).getId();
            }
        }

        public FirebasePrivateWorkoutPlan(
                long sportID,
                String planID,
                boolean customized,
                long facilityID,
                double longitude,
                double latitude,
                String name) {
            this.sportID = sportID;
            this.planID = planID;
            this.customized = customized;
            this.facilityID = facilityID;
            this.longitude = longitude;
            this.latitude = latitude;
            this.name = name;
        }

        public long getSportID() {
            return sportID;
        }

        public String getPlanID() {
            return planID;
        }

        public boolean isCustomized() {
            return customized;
        }

        public long getFacilityID() {
            return facilityID;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public String getName() {
            return name;
        }
    }
}
