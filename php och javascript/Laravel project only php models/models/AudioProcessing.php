<?php

class AudioProcessing {
    
    //TODO: Move this to a config file
    private static $uploadMaxSize = 200000000; //Max upload size 200M. Must also be set in PHP.ini
    
    public static function processAudio($media, $file){
        $extension = $file->getClientOriginalExtension();
        //Check file format
        if ($extension != 'mp3')
        {
        	throw new APIException("Filformatet '" . $extension . "' är inte giltigt. Endast mp3.",
            422);
        }

        //Check and save filesize
        $media->filesize = Input::file('audio')->getSize();
        if ($media->filesize > self::$uploadMaxSize)
        {
            throw new APIException("Max storlek på mp3-filer är 200 MB", 422);
        }

        //Setup filename and path
        $filename = time().urlencode($file->getClientOriginalName()); //.time()
        $placeDestinationPath = MediaOperations::$mediaDestinationPath . $media->place_id . "/";

        //Save audio file (mp3) to disk
        try
        {
        	$file->move($placeDestinationPath, $filename);
        }
        catch (Exception $exception)
        {
            throw new APIException("Ett fel inträffade när ljudfilen skulle sparas", 500);
        }
        $media->url = $placeDestinationPath . $filename;
        $media->thumbnail_url = '\img\blue\radio75px.png';
        return $media;
    }


}