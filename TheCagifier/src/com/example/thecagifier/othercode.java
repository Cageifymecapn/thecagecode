//Extra code written but not yet utilized.






private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, actionCode);
    }

public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmap = (Bitmap) extras.get("data");
        Picture.setImageBitmap(mImageBitmap);
    }



public String getPath(Uri uri) {
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }
    
    
    private void galleryAddPic(Intent data) throws IOException{
    	//Create an image file name
        String timeStamp = 
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        
    	//Sets the storage directory
        File storageDir = new File(
			Environment.getExternalStoragePublicDirectory(
			        Environment.DIRECTORY_PICTURES
			    ), imageFileName);

        File image = File.createTempFile(
                imageFileName, 
                ".jpg", 
                storageDir
            );
        Bitmap theImage = (Bitmap) data.getExtras().get("data");
        MediaStore.Images.Media.insertImage(getContentResolver(), theImage, image.getName() , "Made by Cagifier");

        /*
        //Append the file name to the intent
		File f = image;
        Uri contentUri = Uri.fromFile(f);
        data.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);*/
    
