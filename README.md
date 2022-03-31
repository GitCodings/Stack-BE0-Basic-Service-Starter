# CS122B Homework 2 - The Basic Service

#### [Application Settings](#application-settings)

#### [Endpoints](#endpoints)

1. [GET: Hello](#hello)
2. [GET: Reverse](#reverse)
3. [POST: Math](#math)

## Application Settings

Spring Boot can has a large number of settings that can be set with a file called `application.yml`. \
This file is already provided for you and is placed here for reference.

##### `application.yml`

```yml
spring:
  application:
    name: BasicService

server:
  address: 0.0.0.0
  port: 10020
  error: # These settings are for debugging
    include-exception: true
    include-message: always 

logging:
  file:
    name: ./BasicService.log

basic:
  greeting-message: "Hello there client!"
``` 

# Endpoints

### Order of Validation
All <code>❗ 400: Bad Request</code> Results must be checked first, and returned before any other action is made. \
The order of the checks within <code>❗ 400: Bad Request</code> is not tested as each Result is tested individually.

### JsonInclude
In the case of non-successful results, where values are expected, the values should not be included, for example.
```json
{
   "result": {
      "code": 32,
      "message": "Data contains invalid integers"
   },
   "value": null 
}
```
the <code>value</code> key should not be included: 
```json
{
   "result": {
      "code": 32,
      "message": "Data contains invalid integers"
   }
}
```
This is done by insuring that all <code>null</code> values are dropped by either:
- Having your Model extend <code>ResponseModel<Model></code>, or
- Putting the <code>@JsonInclude(JsonInclude.Include.NON_NULL)</code> on your Model class
  
### Result
All <code>Result</code> objects are available as static constants inside the <code>com.github.klefstad_teaching.cs122b.core.result.BasicResults</code> class.
These can be used rather than creating your own.

## Hello
Endpoint to greet the caller with a simple greeting. It should always return a status of 200 with the proper `greetingMessage` found in the config under: 
```yml
basic:
  greeting-message: <greeting>
```

### Path

```http 
GET /hello
```

### API

<table>
  <tbody>
    <tr>
      <th colspan="3" align="left" width="1100">📤&nbsp;&nbsp;Response</th>
    </tr>
    <tr></tr>
    <tr>
      <th colspan="2" align="left">Model </th>
      <th align="left">Example </th>
    </tr>
    <tr>
      <td colspan="2" align="left"><pre lang="yml">
result: Object
    code: Integer
    message: String
greeting: String</pre></td>
      <td align="left"><pre lang="json">
{
    "result": {
        "code": 10,
        "message": "Greeting sent successfully"
    },
    "greeting": "Hello There!"
}</pre></td>
    </tr>    
    <tr><td colspan="3" ></td></tr>
    <tr></tr>
    <tr>
      <th colspan="3" align="left">📦&nbsp;&nbsp;Results</th>
    </tr>
    <tr></tr>
    <tr>
      <th align="left" width="200">Status</th>
      <th align="left">Code</th>
      <th align="left">Message</th>
    </tr>
    <tr>
      <td><code>✅ 200: Ok</code></td>
      <td>10</td>
      <td>Greeting sent successfully</td>
    </tr>
  </tbody>
</table>

## Reverse
This endpoint accepts a message as a path parameter. The endpoint should take the message and return it reversed inside a `ResponseBody` response described bellow. 

### Path

```http 
GET /reverse/{message}
```

### API

<table>
  <tbody>
    <tr>
      <th colspan="3" align="left" width="1100">🔖&nbsp;&nbsp;Path</th>
    </tr>
    <tr></tr>
    <tr>
      <th align="left">Name</th>
      <th align="left">Type</th>
      <th align="left">Description</th>
    </tr>
    <tr>
      <td>message</td>
      <td><code>String</code></td>
      <td>(Required) Message to reverse (should only contain alphanumeric characters, spaces, and underscores)</td>
    </tr>
    <tr><td colspan="3" ></td></tr>
    <tr></tr>
    <tr>
      <th colspan="3" align="left">📤&nbsp;&nbsp;Response</th>
    </tr>
    <tr></tr>
    <tr>
      <th colspan="2" align="left">Model </th>
      <th align="left">Example </th>
    </tr>
    <tr>
      <td colspan="2" align="left"><pre lang="yml">
result: Object
    code: Integer
    message: String
reversed: String (nullable)</pre></td>
      <td align="left"><pre lang="json">
{
    "result": {
        "code": 20,
        "message": "String successfully reversed"
    },
    "reversed": "dlrow olleh"
}</pre></td>
    </tr>    
    <tr><td colspan="3" ></td></tr>
    <tr></tr>
    <tr>
      <th colspan="3" align="left">📦&nbsp;&nbsp;Results</th>
    </tr>
    <tr></tr>
    <tr>
      <th align="left" width="200">Status</th>
      <th align="left">Code</th>
      <th align="left">Message</th>
    </tr>
    <tr>
      <td><code>✅ 200: Ok</code></td>
      <td>20</td>
      <td>String successfully reversed</td>
    </tr>
    <tr></tr>
    <tr>
      <td><code>❗ 400: Bad Request</code></td>
      <td>21</td>
      <td>String is empty</td>
    </tr>
    <tr></tr>
    <tr>
      <td><code>❗ 400: Bad Request</code></td>
      <td>22</td>
      <td>String contains invalid characters</td>
    </tr>
  </tbody>
</table>

## Math
Takes three integers (numX, numY, numZ) in the form of the request JSON body described bellow and calculates the value for (x * y + z). 

### Path
```http 
POST /math
```

### API

<table>
  <tbody>
    <tr>
      <th colspan="3" align="left" width="1100">📥&nbsp;&nbsp;Request</th>
    </tr>
    <tr></tr>
    <tr>
      <th colspan="2" align="left">Model </th>
      <th align="left">Example </th>
    </tr>
    <tr>
      <td colspan="2" align="left"><pre lang="yml">
numX: Integer
numY: Integer
numZ: Integer</pre></td>
      <td align="left"><pre lang="json">
{
    "numX": 50,
    "numY": 50,
    "numZ": 0
}</pre></td>
    <tr>
      <th align="left">Key</th>
      <th align="left">Required</th>
      <th align="left">Description </th>
    </tr>
    <tr>
      <td><code>numX</code></td><td><code>yes</code></td><td>Must be between (0 and 100) (exclusive)</td>
    </tr>
    <tr></tr>
    <tr>
      <td><code>numY</code></td><td><code>yes</code></td><td>Must be between (0 and 100) (exclusive)</td>
    </tr>
    <tr></tr>
    <tr>
      <td><code>numZ</code></td><td><code>yes</code></td><td>Must be between [-10 and 10] (inclusive)</td>
    </tr>
    <tr><td colspan="3" ></td></tr>
    <tr></tr>
    <tr>
      <th colspan="3" align="left">📤&nbsp;&nbsp;Response</th>
    </tr>
    <tr></tr>
    <tr>
      <th colspan="2" align="left">Model </th>
      <th align="left">Example </th>
    </tr>
    <tr>
      <td colspan="2" align="left"><pre lang="yml">
result: Object
    code: Integer
    message: String
value: String (nullable)</pre></td>
      <td align="left"><pre lang="json">
{
    "result": {
        "code": 30,
        "message": "Calculation successful"
    },
    "value": 2500
}</pre></td>
    </tr>    
    <tr><td colspan="3" ></td></tr>
    <tr></tr>
    <tr>
      <th colspan="3" align="left">📦&nbsp;&nbsp;Results</th>
    </tr>
    <tr></tr>
    <tr>
      <th align="left" width="200">Status</th>
      <th align="left">Code</th>
      <th align="left">Message</th>
    </tr>
    <tr>
      <td><code>✅ 200: Ok</code></td>
      <td>30</td>
      <td>Calculation successful</td>
    </tr>
    <tr></tr>
    <tr>
      <td><code>❗ 400: Bad Request</code></td>
      <td>31</td>
      <td>Data contains missing integers</td>
    </tr>
    <tr></tr>
    <tr>
      <td><code>❗ 400: Bad Request</code></td>
      <td>32</td>
      <td>Data contains invalid integers</td>
    </tr>
  </tbody>
</table>
