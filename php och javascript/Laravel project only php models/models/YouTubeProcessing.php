<?php

class YouTubeProcessing {

    public static function processVideo($media, $youtube_url){
        //Check url format
        //Unique timestamp needed for url save?
        try
        {
            //Parse url and create default URL to Youtube thumbnail.
            parse_str(parse_url($youtube_url, PHP_URL_QUERY), $URLqueryVars);
            $media->thumbnail_url = 'http://img.youtube.com/vi/' . $URLqueryVars['v'] . '/default.jpg';
            $media->url = $URLqueryVars['v'];
        }
        catch (Exception $exception)
        {
        	throw new APIException("Du måste ange en giltig Youtube-länk.",
            422);
        }
        return $media;
    }

}