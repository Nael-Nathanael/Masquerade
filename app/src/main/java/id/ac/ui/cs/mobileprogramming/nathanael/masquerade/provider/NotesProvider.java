package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.dao.NotesDao;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.database.NotesDatabase;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.Notes;

public class NotesProvider extends ContentProvider {
    public static final String SENDER = "sender";
    public static final String CONTENT = "content";
    public static final int SPECIFIC = 2;
    static final String PROVIDER_NAME = "id.ac.ui.cs.mobileprogramming.nathanael.masquerade.provider";
    static final UriMatcher uriMatcher;
    private static final String NOTES_TABLE_NAME = "notes";
    public static final String URL = "content://" + PROVIDER_NAME + "/" + NOTES_TABLE_NAME;
    private static final int ALL = 1;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PROVIDER_NAME,
                NOTES_TABLE_NAME,
                ALL);

        uriMatcher.addURI(PROVIDER_NAME,
                NOTES_TABLE_NAME + "/*",
                SPECIFIC);
    }

    private NotesDao notesDao;

    public NotesProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Notes new_notes = new Notes(
                values.getAsString(CONTENT),
                values.getAsString(SENDER)
        );

        long id = notesDao.insert(new_notes);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public boolean onCreate() {
        notesDao = NotesDatabase.getDatabase(getContext()).notesDao();
        return false;
    }

    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder
    ) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case ALL:
                cursor = notesDao.findAllAsCursor();
                return cursor;
            case SPECIFIC:
                switch (selection) {
                    case "latest":
                        cursor = notesDao.findLatestAsCursor();
                        return cursor;
                    case "stats":
                        return notesDao.getTotal();
                    default:
                        try {
                            int selection_int = Integer.parseInt(selection);
                            return notesDao.findSeveralLatestAsCursor(selection_int);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Please provide selection (latest/integer)");
                        }
                }
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }
}