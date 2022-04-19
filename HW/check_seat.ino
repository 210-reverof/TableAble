#include <WiFiNINA.h>

char ssid[] = "Galaxy S21";
char pass[] = "12345678910";
int status = WL_IDLE_STATUS;
char server[] = "220.69.208.171";
WiFiClient client;

const int FSR_PIN0 = A0;
const int FSR_PIN1 = A1;
const int FSR_PIN2 = A2;
const int FSR_PIN3 = A3;

const float VCC = 4.98;
const float R_DIV = 3230.0;

int seat[4] = {0, 0, 0, 0};
int current[4] = {0, 0, 0, 0};

boolean array_cmp(int *a, int *b) {
  for (int n = 0; n < 4; n++) {
    if (a[n] != b[n]) return false;
  }
  return true;
}

// sql전송
void send_sql() {
  int sum = current[0] + current[1] + current[2] + current[3];
  if (client.connect(server, 80)) {
    Serial.println("Connected...");
    client.print("GET /wifitest.php?temp=");
    client.print(sum);
    client.println(" HTTP/1.1");
    client.println("Host: 220.69.208.171");
    client.println("Connection: close");
    client.println();
  }
  else {
    Serial.println("UnConnected...");
  }

  if (client.connected()) {
    client.stop();
  }
  Serial.println(99);
}

void setup()
{
  // 센서 인식 설정
  Serial.begin(9600);
  pinMode(FSR_PIN0, INPUT);
  pinMode(FSR_PIN1, INPUT);
  pinMode(FSR_PIN2, INPUT);
  pinMode(FSR_PIN3, INPUT);

  // 와이파이 연결
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to Network named: ");
    Serial.println(ssid);
    status = WiFi.begin(ssid, pass);
    if (status == WL_CONNECTED) break;
    delay(10000);
  }
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());
  IPAddress ip = WiFi.localIP();
  IPAddress gateway = WiFi.gatewayIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

}

void loop()
{
  int fsrADC0 = analogRead(FSR_PIN0);
  int fsrADC1 = analogRead(FSR_PIN1);
  int fsrADC2 = analogRead(FSR_PIN2);
  int fsrADC3 = analogRead(FSR_PIN3);

  float fsrV0 = fsrADC0 * VCC / 1023.0;
  float fsrV1 = fsrADC1 * VCC / 1023.0;
  float fsrV2 = fsrADC2 * VCC / 1023.0;
  float fsrV3 = fsrADC3 * VCC / 1023.0;

  float fsrR0 = R_DIV * (VCC / fsrV0 - 1.0);
  float fsrR1 = R_DIV * (VCC / fsrV1 - 1.0);
  float fsrR2 = R_DIV * (VCC / fsrV2 - 1.0);
  float fsrR3 = R_DIV * (VCC / fsrV3 - 1.0);

  float force0;
  float force1;
  float force2;
  float force3;

  float fsrG0 = 1.0 / fsrR0;
  float fsrG1 = 1.0 / fsrR1;
  float fsrG2 = 1.0 / fsrR2;
  float fsrG3 = 1.0 / fsrR3;

  if (fsrR0 <= 600) force0 = (fsrG0 - 0.00075) / 0.00000032639;
  else force0 =  fsrG0 / 0.000000642857;
  if (fsrR1 <= 600) force1 = (fsrG1 - 0.00075) / 0.00000032639;
  else force1 =  fsrG1 / 0.000000642857;
  if (fsrR2 <= 600) force2 = (fsrG2 - 0.00075) / 0.00000032639;
  else force2 =  fsrG2 / 0.000000642857;
  if (fsrR3 <= 600) force3 = (fsrG3 - 0.00075) / 0.00000032639;
  else force3 =  fsrG3 / 0.000000642857;

  //    Serial.println("Force0: " + String(force0) + " g");
  //    Serial.println("Force1: " + String(force1) + " g");
  //    Serial.println("Force2: " + String(force2) + " g");
  //    Serial.println("Force3: " + String(force3) + " g");
  //    Serial.println();

  seat[0] = (force0 > 100) ? 1 : 0;
  seat[1] = (force1 > 100) ? 1 : 0;
  seat[2] = (force2 > 100) ? 1 : 0;
  seat[3] = (force3 > 100) ? 1 : 0;

  // 만약에 변화가 생긴다면
  if (!array_cmp(current, seat)) {
    for (int i = 0; i < 4; i++) {
      current[i] = seat[i];
    }
    send_sql();
    Serial.print(seat[0]);
    Serial.print(seat[1]);
    Serial.print(seat[2]);
    Serial.println(seat[3]);
  }

  delay(1000);
}
