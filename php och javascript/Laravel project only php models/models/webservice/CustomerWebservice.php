<?php

class CustomerWebservice {

	public function postCustomer($data) {

		try {
			$restClient = new RestClient();

			$jsonData = json_encode($data);

			$response = $restClient->post('customers/', $jsonData);

			dd($response);

			$data = json_decode($response);
			foreach ($data as $info) {
				if (isset($info->error)) {
					if ($info->error == 1) {
						throw new Exception('Fel när kund skulle sparas', 1);
					}
				}
			}

		} catch (Exception $e) {
			throw new Exception('Fel när kund skulle sparas', 1);
		}

		return json_decode($response);
	}

	public function putCustomer($id, $data) {

		try {
			$restClient = new RestClient();

			$jsonData = json_encode($data);

			$response = $restClient->put("customers/$id", $jsonData);

			$data = json_decode($response);
			foreach ($data as $info) {
				if (isset($info->error)) {
					if ($info->error == 1) {
						throw new Exception('Fel när kund skulle uppdateras', 1);
					}
				}
			}

		} catch (Exception $e) {
			throw new Exception('Fel när kund skulle sparas', 1);
		}

		return json_decode($response);
	}

	public function getCustomer($id) {

		try {
			$restClient = new RestClient();

			$response = $restClient->get("customers/$id");

			$data = json_decode($response);
			foreach ($data as $info) {
				if (isset($info->error)) {
					if ($info->error == 1) {
						throw new Exception('Fel när kund skulle hämtas', 1);
					}
				}
			}

		} catch (Exception $e) {
			throw new Exception('Fel när kund skulle hämtas', 1);
		}

		return json_decode($response);
	}
}