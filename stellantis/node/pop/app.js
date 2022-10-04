var rs = require('jsrsasign');

var popTokenBuilderUtil = require('./poptoken-builder-node');

var popToken = '';

var bearer = "" + "AAAAAAAAAAAAAAAAAAAAAA.IuY93zyF2gjfAfM3_PV8DFnrnGA.Q3t-YOsQwngsPL8i8SdwVPeYSNkzRN1Fmpkz4XCyU768MhtURvk65iA42f5o5_Xu7DFZ8gDiOEhProjHBZpc5CSrQDuRx05c2x4dTME4tEXl9KVopa0DaibkfZiGLJuiPWDZ2LZudb0J23SwtOKrL7PHq6VJYsQNV0EPxq0I6HxnkLsKt22psHBZ8GChP4jeZL2Md28aU4KrHPxVZq1kUhWPiGF8xirhQ7bJZNKRBDtPGFcq359VUxSkqj6MHPOkqALqXwpkhGZ0uEs3xYXRpxLEEhOVruIvwTglLMEmGxtKPY76rKwNGhpqQtKa7CWExpF0_vMYDp0d4EjgF_hreA";

 



var protectedPrivateKeyPemStr = "" +
"-----BEGIN RSA PRIVATE KEY-----\n" + 
"Proc-Type: 4,ENCRYPTED\n" + 
"DEK-Info: DES-EDE3-CBC,624BBDF4C596582B\n" + 
"\n" + 
"Znm5+Fd29DtjSSzXKCWtkAvyR6BJJAs0qHhhoLWltu/wqd71i/zzO+rBB4niLfvv\n" + 
"086pKvUeNnTMj87pmLgxNzdtQLtmszpWd+y5WAlS894W++o5MobSCE50bVqDS/7I\n" + 
"JNHOVtl5kN/ZH1oizloqzf9ttsxgO3o/RToTqG/PBOPJivzOzvQl/DA6xnsAhUwf\n" + 
"mSDjPqD3VN51ymOBtTiI5Le2A93j0ZLQSHkquump/6GnITv76BaKt5VOX08c73bK\n" + 
"zUenQddGCpLJ6lzk7YNBAQ4F5JA66ZeDgTuOKlVA3f6s69gkS8Zh7JqkkrMg+vpC\n" + 
"Tqc5puEShN1FzTI4yDE6QP/Pvu03AAGIdRle+dhl7vIUkdLc5XC/BSVpzxhQ1+tw\n" + 
"NUV2AyZ8mqcGjpn34Get7c5BmBTGtiVKdVstfJyqDBvP2VaP4Pb2qsxadW5DEl8q\n" + 
"8lwNGV8MvisYmZFUbNsBogxXGQ8tcUFT/yZgrCR0frFQH+tnUFRJl2Fa89MNKa5q\n" + 
"UVIXOB9N/Aivirey7TaydYa57qEzIgY9uuvpKB5f2Ko5+YJ68sVW7mrqIJVl9Epr\n" + 
"U65qNo45nord71RrXKQd42oSpCb9voddK8RjCYFDR6DRaZP+PYKmwGJpaGUErz5s\n" + 
"wN0o6Y6p1pNtSRD/4F76ljBOVQPQbIbuBH02gpV78uSRdGJiDn7qNnKnOtuFkib0\n" + 
"kMbapAoAjnhrC10Ne3D9ggP8nDIXbp/SOAV3ey0Fd+BIdkvfGuD7LolrYkS3kjst\n" + 
"d0hUhxpCpCutSvdU+05fsszpY1waUJdT0yGOVKuVMkg5tuQWPVWA5ueN0A+t5XOn\n" + 
"aM7rbZOPhHRCdDk/DW29Lk4BZJOuH+o7Rk+KEkh+ZL2xa0/3q45yv1hmL31vR3eA\n" + 
"LoIPeH+DqVZdgnUtv20/EMbcF4rJXK/Xd4Bc5bTU3+AOAdUtDLcEuFaJenBi9BIB\n" + 
"LRQ7Z3DRHVr+gcLX82GZArjl7SM1FqyJ5QUOpL+BzfAnCD4oo12O8ydxntJ7kqad\n" + 
"IUyTQbMSYPOHMGVvBtiloA+/J1SSsdBJA5lyF/gEKCOyYoWR/D3urEI5lPb7UsQ3\n" + 
"Qj3pNMLPn7YIh4JNgdKX4aAMoybOUm0b5RcFxSnX/ZkBsVosmsmbWQfHD8xJuqAj\n" + 
"+5Dl9fut2l5dvRx45zj4hesoHyHsNZjKGB0Ntg9k8apsiIhAWzymEmnEEAANQ21Q\n" + 
"+f6/O/UokXGkxk+o35HnNd/TSUjNMUYHaHf7wNGCbnJ7Yi0sctGbk4CjRr5J0Qx2\n" + 
"y09lGRHoA1wz2mZ/2eoJlfI2i6uehQeszX57S7Y0pqBp4tkqovxVrEdO0dz59d2d\n" + 
"QhzL2tpvivyrz4hJGy8K/uM6iH7YtXkZS7cB0cOv6fE9+o16BsuPLFJyHHl4Fwvv\n" + 
"xYAvDBTKS6CW+bXaeAzMQiAro0XePh5SqARwOXmzvHF+ePYJ/M4eJGVmYF3h70wP\n" + 
"twCvYtMCBBg1mb68Uisl/GpbIDwxbE5NIjAOOLDeOjlpFXxaUIw2miXEamYxIrez\n" + 
"I0bSJx0S/vXN4b7/V9ZADr5cpQ5JqtA6NRRb9/FDeJ/kCB+3IgEpSVaQKQEU0jKf\n" + 
"-----END RSA PRIVATE KEY-----";



var objPayload = {};
        objPayload.vin = "ZAREARBN6K7618374";
		objPayload.sgwSN = "TF1170919C15240";
		objPayload.ecuCANID = "18DA10F1";
		objPayload.ecuCertStoreUUID = "0JXS0DD1EeeL9BQIgcb+yA==";
		objPayload.ecuSN = "TF1170919C15247";
		objPayload.userId =  "W00827E";
		objPayload.ecuPolicyType = "1";
		objPayload.externalToolID = "d2fe9f66-e4d0-498a-864a-77c977d4c536";
        objPayload.userToken = bearer;
		objPayload.iat = rs.KJUR.jws.IntDate.get("now");
		objPayload.exp = Math.floor(new Date().getTime() / 1000) + (20 * 60);;
		objPayload.nbf = rs.KJUR.jws.IntDate.get("now");
        
		
		
var data = JSON.stringify(objPayload);

popToken = popTokenBuilderUtil.buildPopTokenWithPassword(bearer, data,  protectedPrivateKeyPemStr, "protecno");
//console.log("\n\n" + popToken);





const https = require('https');



const options = {
  hostname: 'test.ext.authdiag.fcagcv.com',
  port: 443,
  path: '/cloud/v2/getLevel3AuthDiagCert',
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization':' PoP ' + popToken,
    'Content-Length': data.length,
  },
};

process.env["NODE_TLS_REJECT_UNAUTHORIZED"] = 0;


const req = https.request(options, res => {
    console.log("\n");
    console.log(`statusCode: ${res.statusCode}`);
  
  res.on('data', d => {
    process.stdout.write(d);
    console.log("\n");
  });
});

req.on('error', error => {
  console.error(error);
});

req.write(data);
req.end();




