curl -X POST "localhost:9200/leadlet/deal/" -H 'Content-Type: application/json' -d'
 {
      "id" : 200,
      "title" : "deal1",
      "pipeline_id" : 1,
      "stage_id" : 12,
      "create_date" : "2018-07-15T14:12:12",
      "lost_reason" : "başka firma tercihi",
      "score" : 92,
      "products" : [
          "product1",
          "product2"
          ],
      "channel" : "Social Media",
      "source" : "Instagram",
      "value" : 12.002,
      "status" : "open"
  }
 '
 curl -X POST "localhost:9200/leadlet/deal/" -H 'Content-Type: application/json' -d'
 {
      "id" : 201,
      "title" : "deal8",
      "pipeline_id" : 1,
      "stage_id" : 12,
      "create_date" : "2018-07-15T14:12:12",
      "lost_reason" : "başka firma tercihi",
      "score" : 92,
      "products" : [
          "product1",
          "product2"
          ],
      "channel" : "Social Media",
      "source" : "Instagram",
      "value" : 12.002,
      "status" : "open"
  }
 '
curl -X POST "localhost:9200/leadlet/deal/" -H 'Content-Type: application/json' -d'
{
     "id" : 202,
     "title" : "deal2",
     "pipeline_id" : 2,
     "stage_id" : 101,
     "create_date" : "2018-06-15T14:12:12",
     "lost_reason" : "yetersiz ürün",
     "score" : 32,
     "products" : [
         "product1"
      ],
     "channel" : "Social Media",
     "source" : "Instagram",
     "value" : 12.002,
     "status" : "closed"
 }
'
curl -X POST "localhost:9200/leadlet/deal/" -H 'Content-Type: application/json' -d'
{
     "id" : 203,
     "title" : "deal3",
     "pipeline_id" : 1,
     "stage_id" : 102,
     "create_date" : "2018-03-15T14:12:12",
     "score" : 32,
     "products" : [
         "product1"
      ],
     "channel" : "Social Media",
     "source" : "Facebook",
     "value" : 20,
     "status" : "open"
 }
'
curl -X POST "localhost:9200/leadlet/deal/" -H 'Content-Type: application/json' -d'
{
     "id" : 204,
     "title" : "deal4",
     "pipeline_id" : 1,
     "stage_id" : 103,
     "create_date" : "2018-07-12T14:12:12",
     "score" : 32,
     "products" : [
         "product3"
      ],
     "channel" : "Social Media",
     "source" : "Facebook",
     "value" : 20,
     "status" : "open"
 }
'

curl -X POST "localhost:9200/leadlet/deal/" -H 'Content-Type: application/json' -d'
{
     "id" : 205,
     "title" : "deal5",
     "pipeline_id" : 2,
     "stage_id" : 18,
     "create_date" : "2018-07-12T14:12:12",
     "score" : 32,
     "products" : [
         "product3"
      ],
     "channel" : "Social Media",
     "source" : "Facebook",
     "value" : 20,
     "status" : "open"
 }
'

curl -X POST "localhost:9200/leadlet/deal/" -H 'Content-Type: application/json' -d'
{
     "id" : 206,
     "title" : "deal6",
     "pipeline_id" : 2,
     "stage_id" : 19,
     "create_date" : "2018-07-12T14:12:12",
     "score" : 32,
     "products" : [
         "product3"
      ],
     "channel" : "Facebook Form",
     "source" : "Facebook",
     "value" : 20,
     "status" : "open"
 }
'

curl -X POST "localhost:9200/leadlet/deal/" -H 'Content-Type: application/json' -d'
{
     "id" : 207,
     "title" : "deal7",
     "pipeline_id" : 2,
     "stage_id" : 100,
     "create_date" : "2018-07-12T14:12:12",
     "score" : 32,
     "products" : [
         "product3"
      ],
     "channel" : "Live Chat",
     "source" : "Facebook",
     "value" : 20,
     "status" : "open"
 }
'

#curl -X DELETE "localhost:9200/leadlet-*"


curl -u elastic:0SGYXpSLAqHUMpnD4IGsFAy5 -X DELETE "https://8b90cbd3e3a640c4a7088d8a7d161288.europe-west1.gcp.cloud.es.io:9243/leadlet"
