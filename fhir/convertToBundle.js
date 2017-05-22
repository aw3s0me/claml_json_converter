"use strict";

const fs = require("fs");
const path = require("path");
const icdObj = require('./final.json');
let bundle = {
    "resourceType": "Bundle",
    "id": "icd-10",
    "type": "collection",
    "entry": [{
            "fullUrl": "http://hl7.org/fhir/sid/icd-10",
            "resource":{
                "resourceType": "CodeSystem",
                "url": "http://hl7.org/fhir/sid/icd-10",
                "version": "1.0",
                "name": "",
                "concept": []
            }
    }]
};

/**
 * Created by korovin on 5/22/2017.
 */
function convertToBundle() {
    // TODO: read from final.json
    const {diseases} = icdObj;
    bundle.entry[0].resource.concept = diseases.map(disease => {
        return {
            code: disease.code,
            display: disease.name
        }
    });

    fs.writeFile('./bundle.json', JSON.stringify(bundle), err => {
        if (err) {
            return console.error(err);
        }

        console.log('Succesfully saved file');
    })
}

convertToBundle();
