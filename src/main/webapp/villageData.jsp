<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="villageData.css">
    <title>Village Data</title>
</head>
<body>
    <div class="main"><p style="font-family: Cormorant Garamond Light;color:rgba(64,66,51,1);font-size:47px">Village Information</p></div>
    
    <div class="box">
    <ul>    <li>Village Name: ${villageName}</li>
            <li>Population: ${population}</li>
            <li>Male Population: ${male}</li>
            <li>Female Population: ${female}</li>
            <li>Children: ${children}</li>
            <li>Population Density: ${populationDensity}</li>
            <li>Sex Ratio: ${sexRatio}</li>
            <li>Literacy Rate: ${literacyRate}</li>
            <li>Female Literacy Rate: ${femaleLiteracyRate}</li>
            <li>Illiterates: ${illiterates}</li>
            <li>Working Population Percentage: ${workingPopulationPercentage}</li>
            <li>District: ${district}</li>
            <li>Division: ${division}</li>
            <li>Altitude: ${altitude}</li>
            <li>Telephone/STD Code: ${telephoneStdCode}</li>
            <li>Local Language: ${localLanguage}</li>
            <li>Pin Code: ${pinCode}</li>
            <li>National Highways: ${nationalHighways}</li>
            <li>Political Representation: ${politics}</li>
            <li>Schools: ${schools}</li>
            <li>Colleges: ${colleges}</li>
            <li>Health Centres/Hospitals: ${healthCentresHospitals}</li>
            <li>Total Geographical Area: ${totalGeographicalArea}</li>
            <li>Agricultural Area: ${agriculturalArea}</li>
            <li>Irrigated Land: ${irrigatedLand}</li>
            <li>Non-Agricultural Area: ${nonAgriculturalArea}</li>
            <li>Agricultural Commodities: ${agriculturalCommodities}</li>
            <li>Drinking Water: ${drinkingWater}</li>
            <li>Sanitation: ${sanitation}</li>
            <li>Postal Head Office: ${postalHeadOffice}</li>
        </ul>
    </div>
    <div class="green">
            <span class="text">Village Connect</span>
        </div>
</body>
</html>
