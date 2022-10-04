import json

from flask import Flask, render_template
from python_requests import request
from python_requests import RS256
from python_requests.getLevel3AuthDiagCert import secure_request

app = Flask("Stellants login App")


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/token_request')
def token_request():
    decode, token = request.create_token(log=True, return_token=True)
    data = [
        {
            'decode_data': str(decode),
            'token_data': str(token)
        }
    ]
    return render_template('token_info.html', data=data)
    # return request.create_token(True)


@app.route('/signed_token_view')
def signed_token_view():
    decode, signed_token = RS256.signed_token(True)
    data = [
        {
            'signed_token': signed_token,
            'decode': decode
        }
    ]
    return render_template('signed_token.html', data=data)


@app.route('/callback_authorzation')
def callback_authorzation():
    return 'si, voce conseguiu'


@app.route('/getLevel3AuthDiagCert')
def getLevel3AuthDiagCert():
    info = secure_request()

    data = [
        {
            'url': str(info['url']),
            'data': str(info['data']),
            'response': str(info['response'])
        }
    ]

    return render_template('getLevel3AuthDiagCert.html',data= data)


if __name__ == "__main__":
    app.run(debug=True)
