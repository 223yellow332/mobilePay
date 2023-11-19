-- 가맹점 정보 입력
Insert into merchant(merchant_id, progress_code, merchant_name, max_sms_count, category) 
values(1, 'AVAILABLE', '테스트가맹점2', 5, "02");
Insert into merchant(merchant_id, progress_code, merchant_name, max_sms_count, category) 
values(2, 'AVAILABLE', '테스트가맹점1', 5, "02");
Insert into merchant(merchant_id, progress_code, merchant_name, max_sms_count, category) 
values(3, 'WAIT', '테스트가맹점2', 5, "02");

-- 결제 정보 입력
insert into payments(payment_id, merchant_id, pay_amount, carrier_name, gender, merchant_trxid, phone, sms_auth_number, social_number, status_code)
				values(1, 1, 1000, 'SK', 'F', 'order_123444', '01010044885', '123456', '20001225', 'CERT_SUCCESS');
insert into payments(payment_id, merchant_id, pay_amount, carrier_name, gender, merchant_trxid, phone, sms_auth_number, social_number, status_code)
				values(2, 1, 1000, 'SK', 'F', 'order_1234456', '01010044885', '123456', '20001225', 'CERT_FAILURE');