import io
import datetime
import pathlib

import flask
import PIL.Image

bp = flask.Blueprint('api', __name__, url_prefix='/api')


@bp.route('/upload', methods=['POST'])
def upload():
    """upload accepts a labelled image and adds it to the data set for
    training.
    """
    label = flask.request.args.get('label')
    if label is None:
        return {"error", "label query parameters required"}, 400
    
    directory = f'data/{label}/'
    pathlib.Path(directory).mkdir(parents=True, exist_ok=True)
    filepath = f'{directory}/{datetime.datetime.now().isoformat("T")}.png'

    img = PIL.Image.open(io.BytesIO(flask.request.data)) 
    img.thumbnail((32,32))
    img.save(filepath) 
   
    response = {
        'msg': 'image uploaded',
        'path': filpath,
    }
    return response, 201


@bp.route('/train', methods=['POST'])
def train():
    """train triggers and update of the model by training on the current data
    set.
    """
    return flask.Response(
        '{"msg": "Not Implemented"}',
        status=501,
        mimetype="application/json",
    )


@bp.route('/predict', methods=['POST'])
def predict():
    """Predict character using model and uploaded image"""
    return flask.Response(
        '{"msg": "Not Implemented"}',
        status=501,
        mimetype='application/json',
    )
