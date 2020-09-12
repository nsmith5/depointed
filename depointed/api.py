import io
import datetime
import pathlib

import flask
import PIL.Image
import tensorflow
from tensorflow.keras import preprocessing

from . import model

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
    img.thumbnail((256,256))
    img.save(filepath) 
   
    response = {
        'msg': 'image uploaded',
        'path': filepath,
    }
    return response, 201


@bp.route('/train', methods=['POST'])
def train():
    """train triggers and update of the model by training on the current data
    set.
    """
    m = model.create('data')
    m.save('model.tf') 

    response = {
        'msg': 'Model trained',
        'path': f'data/model.tf'
    }
    return response, 201


@bp.route('/predict', methods=['POST'])
def predict():
    """Predict character using model and uploaded image"""
    tmp_file = '/tmp/img.png' 
    img = PIL.Image.open(io.BytesIO(flask.request.data)) 
    img.thumbnail((256,256))
    img.save(tmp_file) 
  
    img = preprocessing.image.load_img(
        tmp_file,
        target_size=(256, 256),
        color_mode='grayscale',
        interpolation='bicubic',
    )

    img = preprocessing.image.img_to_array(img)
    img = tensorflow.expand_dims(img, 0)
    
    m = model.load('model.tf')
    predictions = m.predict(img)
    print(predictions)

    response = {
        'prediction': str(predictions[0]),
    }
    return response, 200
