    function VueCommmon_encrypt(word){
        var key = CryptoJS.enc.Utf8.parse(VAR_GLOBAL_WEBSITE_DEFAULT_CRYPT_KEY);
        var srcs = CryptoJS.enc.Utf8.parse(word);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
        return encrypted.toString();
    }
	function VueCommmon_decrypt(word){
	    var key = CryptoJS.enc.Utf8.parse(VAR_GLOBAL_WEBSITE_DEFAULT_CRYPT_KEY);
	    var decrypt = CryptoJS.AES.decrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
	    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
	}