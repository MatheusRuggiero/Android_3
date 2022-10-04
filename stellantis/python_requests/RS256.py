from cryptography.hazmat.primitives import serialization
import jwt
from python_requests.request import create_token


def signed_token(log):

    header_token = {
        "typ": "JWT",
        "alg": "RS256",
        "x5t": "fo6ST8SXBnMkGP_8ZOPLOdqA8hU",
        "kid": "fo6ST8SXBnMkGP_8ZOPLOdqA8hU"
    }

    payload_token,token = create_token(log=log, return_token=True)

    private_key = open('../key/private.pem', 'r').read()

    key = serialization.load_pem_private_key(
        private_key.encode(),
        password=b'protecno'
    )

    new_token = jwt.encode(
        payload=payload_token,
        headers=header_token,
        algorithm='RS256',
        key=key,
    )
    if log:
        print('\n')
        print(new_token)

    decoded = jwt.decode(new_token, options={"verify_signature": False})

    return decoded , new_token


signed_token(log=True)