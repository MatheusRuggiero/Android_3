import requests
import jwt


def create_token(log=False, return_token=False):
    """
    Esta função tem o objetivo de fazer a requezição de um token e extrair as informações do mesmo.

    :param log: Boolean -> se desejar ver as saidas desta função deve setar esta variavel como True
    :return: deve retornar um JSON contendo informações do token recebido

    """

    url = "https://sts-dev.fiat.com/adfs/oauth2/token"

    data = {
        "client_id": "3a12bfe0-83f6-4ab6-abb5-3aa78fbb9eea",
        "scope": "openid",
        "username": "FGEXTRANET\W00827E",
        "password": "rNe-0825.TecProjeto",
        "grant_type": "password",
    }

    response = requests.post(url, data=data)
    token = response.json()
    token = token['id_token']

    decoded = jwt.decode(token, options={"verify_signature": False})  # works in PyJWT >= v2.0

    if log:
        print('Log:', log)
        print(token)
        print(decoded)

    if return_token:
        return decoded, token

    return decoded


def create_authorization(log=False):
    # monta a url

    response_type = 'code'
    response_mode = 'form_post'
    resource = 'test.authdiag.fcagcv.com_TECNOMOTOR'
    client_id = '3a12bfe0-83f6-4ab6-abb5-3aa78fbb9eea'
    redirect_uri = '127.0.0.1:5000/callback_authorzation'

    # outras = 'https://sts-dev.fiat.com/adfs/oauth2/authorize?response_type=code&response_mode=form_post&resource=test.authdiag.fcagcv.com_TECNOMOTOR&client_id=3a12bfe0-83f6-4ab6-abb5-3aa78fbb9eea&redirect_uri=fca-app://test.authdiag.fcagcv.com'

    url = 'https://sts-dev.fiat.com/adfs/oauth2/authorize?' \
          f'response_type={response_type}&' \
          f'response_mode={response_mode}&' \
          f'resource={resource}&' \
          f'client_id={client_id}&' \
          f'redirect_uri={redirect_uri}'

    if log:
        print(url)

    return url


def full_token(log=False):
    url = "https://sts-dev.fiat.com/adfs/oauth2/token"

    data = {
        "client_id": "3a12bfe0-83f6-4ab6-abb5-3aa78fbb9eea",
        "scope": "openid",
        "username": "FGEXTRANET\W00827E",
        "password": "rNe-0825.TecProjeto",
        "grant_type": "password",
    }

    response = requests.post(url, data=data)
    token = response.json()

    if log:
        print(token)

    return token

e,d = create_token(log=True,return_token=True)
print(type(str(e)))
print(e)
print(type(str(d)))
print(d)

token = full_token(True)
print(token['id_token'])
