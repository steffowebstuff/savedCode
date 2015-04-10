<?php

class RestClient {

	private $endpoint;
	private $curl;
	private $accessToken;
	private $clientSecret;
	private $contentType;
	private $accepts;

	public function __construct() {
		$this->endpoint = Config::get('fortnox.endpoint');
		$this->accessToken = Config::get('fortnox.accessToken');
		$this->clientSecret = Config::get('fortnox.clientSecret');
		$this->contentType = Config::get('fortnox.contentType');
		$this->accepts = Config::get('fortnox.accepts');
	}

	public function get($entity) {	
	  	$curl = curl_init($this->endpoint . $entity);
	  	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);	 
	  	curl_setopt($curl, CURLOPT_HTTPHEADER, array(
			'Access-Token: '. $this->accessToken.'',
			'Client-Secret: '. $this->clientSecret .'',
			'Content-Type: '. $this->contentType .'',
			'Accept: '. $this->accepts .''
		));

	  	$curl_response = curl_exec($curl);
	  	curl_close($curl);
	  
	  	return $curl_response;
	}

	public function post($entity, $data) {
	  	$ch = curl_init($this->endpoint . $entity);
	  	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);	 
	  	curl_setopt($ch, CURLOPT_POST, true);
	  	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
	  	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
	  	curl_setopt($ch, CURLOPT_HTTPHEADER, array(
			'Access-Token: '. $this->accessToken.'',
			'Client-Secret: '. $this->clientSecret .'',
			'Content-Type: '. $this->contentType .'',
			'Accept: '. $this->accepts .''
		));

	  	#$ch_response = curl_exec($ch);

	  	if( ! $response = curl_exec($ch) ) {
		    die(curl_error($ch));
		}
	  	curl_close($ch);

	  	return $response;
	}

	public function put($entity, $data) {
	  	$curl = curl_init($this->endpoint . $entity);
	  	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);	 
	  	curl_setopt($curl, CURLOPT_CUSTOMREQUEST, 'PUT');
	  	curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
	  	curl_setopt($curl, CURLOPT_HTTPHEADER, array(
			'Access-Token: '. $this->accessToken.'',
			'Client-Secret: '. $this->clientSecret .'',
			'Content-Type: '. $this->contentType .'',
			'Accept: '. $this->accepts .''
		));

	  	$curl_response = curl_exec($curl);
	  	curl_close($curl);

	  	return $curl_response;
	}

}