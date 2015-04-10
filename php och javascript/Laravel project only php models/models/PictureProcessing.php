<?php

class PictureProcessing {
    
    //TODO: Move this to a config file
    private static $pictureMaxWidth = 800;
    private static $thumbnailHeight = 120;

    private static $logoMaxHeight = 400;
    
    public static function processLogoPicture($customer, $logo){
        $extension = $logo->getClientOriginalExtension();
        //Check file format
        self::checkPictureType($extension);

        //Setup filename and path
        $filename = $customer->id . '_' . time() . '.' . $extension;
        $destinationPath = MediaOperations::$logoDestinationPath;

        //Get picture size
        $fullSize = getimagesize($logo);
		$fullWidth = $fullSize[0];
		$fullHeight = $fullSize[1];
        $ratio = $fullHeight / $fullWidth;

        //Save original to disk
        try
        {
        	$logo->move($destinationPath, $filename);
        }
        catch (Exception $exception)
        {
            throw new APIException("Ett fel inträffade när bilden skulle sparas", 500);
        }
        $customer->logo_url = $destinationPath . $filename;


        //Create thumb
        //$thumbDestinationPath = $placeDestinationPath . "thumbs/";
        //if (!is_dir($thumbDestinationPath))
        //{
        //    mkdir($thumbDestinationPath);
        //}
        //$media->thumbnail_url = $thumbDestinationPath . $filename;
        //try
        //{
        //    self::resizeAndSavePicture($media->url, $extension, $fullWidth, $fullHeight,
        //        self::$thumbnailHeight / $ratio, self::$thumbnailHeight, $media->thumbnail_url);
        //}
        //catch (Exception $exception)
        //{
        //    throw new APIException("Ett fel inträffade när bilden skulle sparas", 500);
        //}

        //Resize logo if necessary
        try
        {
        	if ($fullHeight > self::$logoMaxHeight)
            {
                self::resizeAndSavePicture($customer->logo_url, $extension, $fullWidth, $fullHeight,
                    self::$logoMaxHeight / $ratio, self::$logoMaxHeight, $customer->logo_url);
            }
        }
        catch (Exception $exception)
        {
            throw new APIException("Ett fel inträffade när bilden skulle sparas", 500);
        }
        return $customer;
    }
    
    
    public static function processMediaPicture($media, $picture){
        $extension = $picture->getClientOriginalExtension();
        //Check file format
        self::checkPictureType($extension);
		//Save originalname under nickname or something if you want the original name to be displayed.
        //Setup filename and path
        $filename = time().urlencode($picture->getClientOriginalName()); //.time()
        $placeDestinationPath = MediaOperations::$mediaDestinationPath . $media->place_id . "/";

        //Get picture size
        $fullSize = getimagesize($picture);
		$fullWidth = $fullSize[0];
		$fullHeight = $fullSize[1];
        $ratio = $fullHeight / $fullWidth;

        //Save original to disk
        try
        {
        	$picture->move($placeDestinationPath, $filename);
        }
        catch (Exception $exception)
        {
            throw new APIException("Ett fel inträffade när bilden skulle sparas", 500);
        }
        $media->url = $placeDestinationPath . $filename;


        //Create thumb
        $thumbDestinationPath = $placeDestinationPath . "thumbs/";
        if (!is_dir($thumbDestinationPath))
        {
        	mkdir($thumbDestinationPath);
        }
        $media->thumbnail_url = $thumbDestinationPath . $filename;
        try
        {
            self::resizeAndSavePicture($media->url, $extension, $fullWidth, $fullHeight,
                self::$thumbnailHeight / $ratio, self::$thumbnailHeight, $media->thumbnail_url);
        }
        catch (Exception $exception)
        {
            throw new APIException("Ett fel inträffade när bilden skulle sparas", 500);
        }

        //Resize original if necessary
        try
        {
        	if ($fullWidth > self::$pictureMaxWidth)
            {
                self::resizeAndSavePicture($media->url, $extension, $fullWidth, $fullHeight,
                    self::$pictureMaxWidth, self::$pictureMaxWidth * $ratio, $media->url);
            }
        }
        catch (Exception $exception)
        {
            throw new APIException("Ett fel inträffade när bilden skulle sparas", 500);
        }

        $media->filesize = filesize($media->url);
        return $media;
    }

    public static function checkPictureType($extension){
        if ($extension == 'jpeg' || $extension == 'jpg' || $extension == 'png' || $extension == 'gif')
        {
            return true;
        } else
        {
            throw new APIException("Filformatet '" . $extension . "' är inte giltigt. Endast jpeg, jpg, png och gif.",
            422);
        }
    }

    public static function resizeAndSavePicture($originalPic, $extension, $old_width, $old_height,
            $new_width, $new_height, $outputFilename){

        $tmp_img = imagecreatetruecolor($new_width, $new_height );
        if ($extension == 'jpeg'){
            $im = imagecreatefromjpeg($originalPic);
            imagecopyresampled($tmp_img, $im, 0, 0, 0, 0, $new_width, $new_height, $old_width, $old_height );
            imagejpeg($tmp_img, $outputFilename);
        }
        else if ($extension == 'jpg'){
            $im = imagecreatefromjpeg($originalPic);
            imagecopyresampled($tmp_img, $im, 0, 0, 0, 0, $new_width, $new_height, $old_width, $old_height );
            imagejpeg($tmp_img, $outputFilename);
        }
        else if ($extension == 'png'){
            $im = imagecreatefrompng($originalPic);
            imagecopyresampled($tmp_img, $im, 0, 0, 0, 0, $new_width, $new_height, $old_width, $old_height );
            imagepng($tmp_img, $outputFilename);
        }
        else if ($extension == 'gif'){
            $im = imagecreatefromgif($originalPic);
            imagecopyresampled($tmp_img, $im, 0, 0, 0, 0, $new_width, $new_height, $old_width, $old_height );
            imagegif($tmp_img, $outputFilename);
        }
    }
}