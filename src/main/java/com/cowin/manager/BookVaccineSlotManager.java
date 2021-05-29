package com.cowin.manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cowin.model.Beneficiary;
import com.cowin.model.BeneficiaryModel;
import com.cowin.model.Captcha;
import com.cowin.model.CenterModel;
import com.cowin.model.ConfirmOtpResponse;
import com.cowin.model.GenerateOtpResponse;
import com.cowin.model.SessionByPinResponse;
import com.cowin.util.Utilities;

@RestController
public class BookVaccineSlotManager {

	@Value("${generate.otp.url}")
	private String generateOtpUrl;

	@Value("${confirm.otp.url}")
	private String confirmOtpUrl;

	@Value("${session.pin.url}")
	private String sessionByPinUrl;

	@Value("${beneficiaries.url}")
	private String beneficiariesUrl;

	@Value("${schedule.appointment.url}")
	private String scheduleAppointmentUrl;
	
	@Value("${cancel.url}")
	private String cancelUrl;

	@Value("${captcha.url}")
	private String captchaUrl;

	@Value("${mobile.no}")
	private String mobile;
	
	@Value("${pincode")
	private String pinCode;

	private String bearerToken1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhNGI4MWE5OS0zYTVlLTQ4MDgtOGQ2Yy00N2JiMjE2YWQ3MzIiLCJ1c2VyX2lkIjoiYTRiODFhOTktM2E1ZS00ODA4LThkNmMtNDdiYjIxNmFkNzMyIiwidXNlcl90eXBlIjoiQkVORUZJQ0lBUlkiLCJtb2JpbGVfbnVtYmVyIjo5NzQyNTIxODg3LCJiZW5lZmljaWFyeV9yZWZlcmVuY2VfaWQiOjg0MjY3MDE1NTI0NjIwLCJzZWNyZXRfa2V5IjoiYjVjYWIxNjctNzk3Ny00ZGYxLTgwMjctYTYzYWExNDRmMDRlIiwidWEiOiJNb3ppbGxhLzUuMCAoV2luZG93cyBOVCAxMC4wOyBXaW42NDsgeDY0KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvOTAuMC40NDMwLjkzIFNhZmFyaS81MzcuMzYiLCJkYXRlX21vZGlmaWVkIjoiMjAyMS0wNS0xM1QxNTozMDowMS43NDFaIiwiaWF0IjoxNjIwOTE5ODAxLCJleHAiOjE2MjA5MjA3MDF9.5Al6sygZOcqkxYnS73PEAZ0RrY8BAyRmDbreY-kJJvE";
	private String secret = "U2FsdGVkX19mD56KTNfQsZgXJMwOG7u/6tuj0Qvil1LEjx783oxHXGUTDWYm+XMYVGXPeu+a24sl5ndEKcLTUQ==";
	RestTemplate restTemplate = new RestTemplate();

	@PostConstruct
	public void init() throws InterruptedException {
		bookSlot();
	}

	@GetMapping("/book")
	public boolean bookSlot() throws InterruptedException {

		/*
		 * System.out.println(); String txnId = generateOTP(mobile); Scanner sc = new
		 * Scanner(System.in); String otp = getInput();
		 */
		String bearerToken = bearerToken1;
		//String bearerToken = confirmOTP(otp, txnId);

		// String
		//
		// String
		// bearerToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIxMTYwYWZkNS0yMjc3LTRiMzktYTM5Ni1iMzczNmMzODZhZmEiLCJ1c2VyX3R5cGUiOiJCRU5FRklDSUFSWSIsInVzZXJfaWQiOiIxMTYwYWZkNS0yMjc3LTRiMzktYTM5Ni1iMzczNmMzODZhZmEiLCJtb2JpbGVfbnVtYmVyIjo5NzQyNTIxODg3LCJiZW5lZmljaWFyeV9yZWZlcmVuY2VfaWQiOjY2NTYzNzMzMzMxMjgwLCJ0eG5JZCI6IjIwYjBiM2UxLWYxMDUtNDU3Yy05N2RjLWEzODg3MTRiYTU5NCIsImlhdCI6MTYyMDg5ODI3OSwiZXhwIjoxNjIwODk5MTc5fQ.pmDpxbX9qyVgrAaT2SrMLW0K0q5wDPfRtGOO0D3o-yE";
		BeneficiaryModel beneficiary = getBeneficiaries(bearerToken);
		while (true) {
			CenterModel center = checkAppointMent();

			if (center != null) {
				scheduleAppointment(beneficiary, bearerToken, center);
				return true;
			} else {
				Thread.sleep(10000);
			}
		}
	}

	private String scheduleAppointment(BeneficiaryModel beneficiaryModel, String bearerToken, CenterModel center) {

		String captcha = getCaptcha(bearerToken);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
		header.setBearerAuth(bearerToken);

		Map<String, Object> param = new HashMap<>();
		param.put("dose", "1");
		param.put("session_id", center.getSession_id());
		param.put("slot", center.getSlots().get(0));
		param.put("captcha", captcha);

		List<String> beneficiaryList = new ArrayList<>();
		for (Beneficiary beneficiary : beneficiaryModel.getBeneficiaries()) {

			beneficiaryList.add(beneficiary.getBeneficiary_reference_id());

		}
		param.put("beneficiaries", beneficiaryList);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(param, header);

		ResponseEntity<String> reponse = restTemplate.postForEntity(scheduleAppointmentUrl, entity, String.class);
		System.out.println(reponse.getBody());
		return reponse.getBody();

	}

	public String generateOTP(String phoneNo) {

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");

		Map<String, String> param = new HashMap<>();
		param.put("mobile", mobile);
		param.put("secret", secret);

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, header);

		ResponseEntity<GenerateOtpResponse> reponse = restTemplate.postForEntity(generateOtpUrl, entity,
				GenerateOtpResponse.class);
		System.out.println(reponse.getBody().getTxnId());
		return reponse.getBody().getTxnId();

	}

	public String confirmOTP(String otp, String txnId) {

		String otpSha = getSha256Otp(otp);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");

		Map<String, String> param = new HashMap<>();
		param.put("otp", otpSha);
		param.put("txnId", txnId);

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, header);

		ResponseEntity<ConfirmOtpResponse> reponse = restTemplate.postForEntity(confirmOtpUrl, entity,
				ConfirmOtpResponse.class);
		System.out.println("BearerToken " + reponse.getBody().getToken());

		return reponse.getBody().getToken();

	}

	public SessionByPinResponse getSlotByPin(String pinCode) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		LocalDate localDate = LocalDate.now();// For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedString = localDate.format(formatter);

		sessionByPinUrl = sessionByPinUrl + "?pincode=" + pinCode + "&date=" + formattedString;
		ResponseEntity<SessionByPinResponse> response = restTemplate.exchange(sessionByPinUrl, HttpMethod.GET, entity,
				SessionByPinResponse.class);
		return response.getBody();

	}

	public void cancelAppointment(String bearerToken) {

		String beneficiaryId = "84267015524620";
		String appointmentId = "a7a45185-f3f3-49b8-831d-5a452416bfb3";
		
		List<String> beneficiariesToCancel = new ArrayList<String>();
		beneficiariesToCancel.add(beneficiaryId);
		
		HttpHeaders header = getHeader(bearerToken);

		Map<String, Object> param = new HashMap<>();
		param.put("appointment_id", appointmentId);
		param.put("beneficiariesToCancel", beneficiariesToCancel);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(param, header);

		ResponseEntity<String> reponse = restTemplate.postForEntity(cancelUrl, entity,
				String.class);
		System.out.println(reponse.getBody());
	}

	public BeneficiaryModel getBeneficiaries(String bearerToken) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
			headers.setBearerAuth(bearerToken);

			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

			ResponseEntity<BeneficiaryModel> response = restTemplate.exchange(beneficiariesUrl, HttpMethod.GET, entity,
					BeneficiaryModel.class);
			
			cancelAppointment(bearerToken);
			// ResponseEntity <BeneficiaryModel> response =
			// restTemplate.exchange(beneficiariesUrl, HttpMethod.GET, entity,
			// BeneficiaryModel.class);
			System.out.println(response.getBody());
			return response.getBody();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public CenterModel checkAppointMent() {
		SessionByPinResponse slots = getSlotByPin(pinCode);
		for (CenterModel center : slots.getSessions()) {
			if (center.getMin_age_limit() < 45 && center.getAvailable_capacity() > 0) {
				return center;
			}
		}
		return null;

	}

	public String getCaptcha(String bearerToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
		headers.setBearerAuth(bearerToken);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<Captcha> reponse = restTemplate.postForEntity(captchaUrl, entity, Captcha.class);

		System.out.println(reponse.getBody().getCaptcha());

		Utilities.createCaptchaImage(reponse.getBody().getCaptcha());

		String captcha = getInput();
		return captcha;
	}

	private String getInput() {
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}

	private HttpHeaders getHeader(String bearerToken) {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");

		header.setBearerAuth(bearerToken);

		return header;
	}

	private String getSha256Otp(String otp) {
		String sha256hex = DigestUtils.sha256Hex(otp);
		return sha256hex;
	}
}
