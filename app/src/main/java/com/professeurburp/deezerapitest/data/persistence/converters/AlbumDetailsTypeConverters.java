package com.professeurburp.deezerapitest.data.persistence.converters;

import androidx.room.TypeConverter;

import com.professeurburp.deezerapitest.R;
import com.professeurburp.deezerapitest.data.model.TrackInfo;
import com.professeurburp.deezerapitest.data.rest.DeezerListResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Room Type converter so that dates can safely be converted to their long counterpart,
 * as Room is not able to store Date values.
 */
public class AlbumDetailsTypeConverters {

    private static final String TRACK_SEPARATOR = "||";
    private static final String TRACK_FIELD_SEPARATOR = "|";

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }

    /**
     * Flattening here is used to test the Room TypeConverter object.
     * We could add a relationship, too, but we would then have to manage
     * relationship retrieval, so that this would never be made on the UI
     * thread, as Room uses lazy relationships loading.
     * <p>
     * Will consider using a relationship later, but first, let's get things done.
     * <p>
     * Also, this would break if any track name contains a '|' character.
     * Again, let's get things done for now, we'll find a proper separator later on,
     * as '|' shouldn't be that usual in track names, and we don't want to use too long
     * a separator string, to keep field as short as possible in db.
     *
     * @param tracksResponse Track list to be flattened
     * @return Flattened version as a String of a track list.
     */
    @TypeConverter
    public static String flattenTracks(DeezerListResponse<TrackInfo> tracksResponse) {

        List<TrackInfo>  trackInfos = tracksResponse.getValues();

        if (trackInfos == null || trackInfos.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        int trackNumber = trackInfos.size();

        for (int index = 0; index < trackNumber; index++) {
            TrackInfo trackInfo = trackInfos.get(index);
            builder.append(flattenTrack(trackInfo));

            if (index < trackNumber - 1) {
                // Add a track separator for all track except last one.
                builder.append(TRACK_SEPARATOR);
            }
        }

        return builder.toString();
    }

    @TypeConverter
    public static DeezerListResponse<TrackInfo> hydrateTracks(String flattenedTracks) {

        // We won't ever return any null value, in order to ease error management later on.
        // Also, it is very unlikely that a track list would be empty...
        DeezerListResponse<TrackInfo> result = new DeezerListResponse<>();
        result.setValues(new ArrayList<>());


        if (flattenedTracks == null || flattenedTracks.trim().length() == 0) {
            return result;
        }

        String[] trackArray = flattenedTracks.split("\\|\\|");
        for (String s : trackArray) {
            result.getValues().add(hydrateTrack(s));
        }

        return  result;
    }


    /**
     * Flattens a single track as a string
     *
     * @param trackInfo TrackInfo to be flattened
     * @return Flattened version of the provided TrackInfo, as a String.
     */
    private static String flattenTrack(TrackInfo trackInfo) {

        return trackInfo.getId() +
                TRACK_FIELD_SEPARATOR +
                (trackInfo.getTitle() != null && trackInfo.getTitle().length() > 0 ?
                        trackInfo.getTitle() : R.string.track_unknown_title) +
                TRACK_FIELD_SEPARATOR +
                trackInfo.getDuration() +
                TRACK_FIELD_SEPARATOR +
                trackInfo.getRank();
    }

    private static TrackInfo hydrateTrack(String flattenedTrack) {

        String[] trackFields = flattenedTrack.split("\\|");
        TrackInfo trackInfo = new TrackInfo();

        trackInfo.setId(Integer.parseInt(trackFields[0]));
        trackInfo.setTitle(trackFields[1]);
        trackInfo.setDuration(Integer.parseInt(trackFields[2]));
        trackInfo.setRank(Integer.parseInt(trackFields[3]));

        return trackInfo;
    }
}