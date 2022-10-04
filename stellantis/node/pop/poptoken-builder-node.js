
/**
  @module: tmo-poptoken-builder
  @description: Defines the functions for the PoP Token Builder.
  @version: 1.0
**/
var rs = require('jsrsasign');

module.exports = {
	buildPopTokenWithPassword: function (bearer, data, encryptedPrivateKeyPemString, password) {

		var objClaim = {};
		var strToken = '';
		
		var strHash = rs.KJUR.crypto.Util.sha256(data);
		var strEdtsHashB64U = rs.hextob64(strHash);

		objClaim.iat = rs.KJUR.jws.IntDate.get("now");
		objClaim.exp = Math.floor(new Date().getTime() / 1000) + (20 * 60);
		objClaim.digest = strEdtsHashB64U;
		
		//console.log("\n\n\n\n\n" + strEdtsHashB64U);


			
		var keyObj = rs.KEYUTIL.getKey(encryptedPrivateKeyPemString, password)
		
		var sClaim = JSON.stringify(objClaim);
		var alg = 'RS256';
		var pHeader = { 'typ': 'JWT', 'alg': alg, 'kid' : 'f2a4de95634643dabff450e870e220f6_tm_br'};
		var sHeader = JSON.stringify(pHeader);

		

		var privateKeyPemString = encryptedPrivateKeyPemString;

		strToken = rs.KJUR.jws.JWS.sign(null, sHeader, sClaim, keyObj);
		
		return strToken;
	},


};
