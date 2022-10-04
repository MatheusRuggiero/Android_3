from python_requests import RS256
import requests


def secure_request():
    decode, signed_token = RS256.signed_token(False)

    url = "https://test.ext.authdiag.fcagcv.com/cloud/v2/getLevel3AuthDiagCert"

    data = {
        "vin": "ZAREARBN6K7618374",  # este dado sera validado com um veículo real em breve
        "sgwSN": "TF1170919C15240",  # este dado sera validado com um veículo real em breve
        "ecuCANID": "18DA10F1",  # este dado sera validado com um veículo real em breve
        "ecuCertStoreUUID": "0JXS0DD1EeeL9BQIgcb+yA==",  # este dado sera validado com um veículo real em breve --- d095d2d0-30f5-11e7-8bf4-140881c6fec8
        "ecuSN": "TF1170919C15247",  # este dado sera validado com um veículo real em breve

        "userId": "3a12bfe0-83f6-4ab6-abb5-3aa78fbb9eea",
        "ecuPolicyType": "1",
        "externalToolID": "d2fe9f66-e4d0-498a-864a-77c977d4c536",

        "userToken": signed_token,
        "iat": decode['iat'],
        "exp": decode['exp'],
        "nbf": decode['nbf']
    }

    response = requests.post(url, data=data, verify=False)

    retorno = {
        'url': url,
        'data': data,
        'response': response
    }

    print(response)

    return retorno


