from tensorflow import keras
from tensorflow.keras import layers
from tensorflow.keras import preprocessing
from tensorflow.keras import models

def create(directory):
    input_shape = (256, 256, 1)
    
    ds = preprocessing.image_dataset_from_directory(
        directory,
        labels='inferred',
        label_mode='categorical',
        color_mode='grayscale',
        image_size=(256, 256),
        interpolation='bicubic',
        batch_size=32,
    )
    
    model = keras.Sequential([
        layers.experimental.preprocessing.Rescaling(1./255, input_shape=input_shape),
        layers.Conv2D(32, kernel_size=(3, 3), activation="relu"),
        layers.MaxPooling2D(pool_size=(2, 2)),
        layers.Conv2D(64, kernel_size=(3, 3), activation="relu"),
        layers.MaxPooling2D(pool_size=(2, 2)),
        layers.Flatten(),
        layers.Dropout(0.5),
        layers.Dense(len(ds.class_names), activation="softmax"),
    ])
    
    model.compile(
        loss='categorical_crossentropy',
        optimizer='adam',
        metrics=['accuracy'],
    )
    model.fit(ds, epochs=10, batch_size=32)
    return model


def load(path):
    return models.load_model(path)
