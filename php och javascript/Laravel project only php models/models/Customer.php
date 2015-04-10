<?php

use Illuminate\Auth\UserInterface;
use Illuminate\Auth\Reminders\RemindableInterface;

class Customer extends BaseModel implements UserInterface, RemindableInterface {

	/**
	 * The database table used by the model.
	 *
	 * @var string
	 */
	protected $table = 'customer';


	public static $rules = array(
		'name' => 'required|max:255',
		'email' => 'required|email|unique:customer',
		'password' => 'required|alpha_num|min:6|confirmed',
		'password_confirmation' => 'required|alpha_num|min:6',
		'orgnr' => 'required|digits:10',
		'zipcode' => 'required|digits:5',
		'address' => 'required|max:100',
		'city' => 'required|max:100',
		'phone' => 'required|numeric'
	);

	public function places()
    {
        return $this->hasMany('Place');
    }

	/**
	 * The attributes excluded from the model's JSON form.
	 *
	 * @var array
	 */
	protected $hidden = array('password');

	/**
	 * Get the unique identifier for the user.
	 *
	 * @return mixed
	 */
	public function getAuthIdentifier()
	{
		return $this->getKey();
	}

	/**
	 * Get the password for the user.
	 *
	 * @return string
	 */
	public function getAuthPassword()
	{
		return $this->password;
	}

	/**
	 * Get the e-mail address where password reminders are sent.
	 *
	 * @return string
	 */
	public function getReminderEmail()
	{
		return $this->email;
	}

	public function belongsToPlace($id)
	{
	   return $this->places->contains($id);
	}

}