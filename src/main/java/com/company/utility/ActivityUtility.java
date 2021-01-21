package com.company.utility;

import com.company.common.Database;
import com.company.common.exceptions.DuplicateException;
import com.company.common.exceptions.MandatoryException;
import com.company.common.exceptions.NotFoundException;
import com.company.models.Activity;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class ActivityUtility {
    private ActivityUtility() {
        throw new UnsupportedOperationException();
    }

    public static Activity findActivityById(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        Activity activity = Database.activities.find(filter).first();
        if (activity == null) {
            throw new NotFoundException("Activity ID");
        }
        return activity;
    }

//    // TODO
//    private static Activity findActivityByActorName(String actorName) {
//        Bson filter = eq("name", activityname);
//        return Database.activities.find(filter).first();
//    }

    public static ObjectId insertActivity(Activity activity) {
        InsertOneResult result = Database.activities.insertOne(activity);
        return result.getInsertedId().asObjectId().getValue();
    }

    public static boolean deleteActivity(ObjectId id) throws NotFoundException {
        Bson filter = eq("_id", id);
        if (Database.activities.deleteOne(filter).getDeletedCount() >= 1 ? true : false) {
            return true;
        }
        throw new NotFoundException("Activity ID");
    }
}
