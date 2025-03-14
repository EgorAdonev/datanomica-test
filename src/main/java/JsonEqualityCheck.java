import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonEqualityCheck {

  private static boolean areJsonsEqual(String firstJson, String secondJson) throws JSONException {
    JSONObject obj1 = new JSONObject(firstJson);
    JSONObject obj2 = new JSONObject(secondJson);
    return areJsonObjectsEqual(obj1, obj2);
  }

  private static boolean areJsonObjectsEqual(JSONObject obj1, JSONObject obj2) throws JSONException {
    if (obj1.length() != obj2.length()) {
      return false;
    }
    Iterator<String> keys = obj1.keys();
    while (keys.hasNext()) {
      String key = keys.next();
      //нет ключа, который есть в 1-м объекте
      if (!obj2.has(key)) {
        return false;
      }

      Object value1 = obj1.get(key);
      Object value2 = obj2.get(key);
      if (value1 instanceof JSONArray && value2 instanceof JSONArray) {
        //проверка массивов
        if (!areJsonArraysEqual((JSONArray) value1, (JSONArray) value2)) {
          return false;
        }
        //проверка объектов
      } else if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
        if (!areJsonObjectsEqual((JSONObject) value1, (JSONObject) value2)) {
          return false;
        }
        //проверка простых типов
      } else if (!value1.equals(value2)) {
        return false;
      }
    }

    return true;
  }

  private static boolean areJsonArraysEqual(JSONArray firstHobbiesArr, JSONArray secondHobbiesArr) throws JSONException {
    if (firstHobbiesArr.length() != secondHobbiesArr.length()) {
      return false;
    }
    Set<Object> firstHobbies = new HashSet<>();
    Set<Object> secondHobbies = new HashSet<>();

    for (int i = 0; i < firstHobbiesArr.length(); i++) {
      firstHobbies.add(firstHobbiesArr.get(i));
    }
    for (int i = 0; i < secondHobbiesArr.length(); i++) {
      secondHobbies.add(secondHobbiesArr.get(i));
    }

    return firstHobbies.equals(secondHobbies);
  }

  public static void main(String[] args) throws JSONException {
    String firstJson = """
        {

          "name": "Иван",

          "age": 30,

          "hobbies": ["programming", "reading"]

        }""";
    String secondJson = """
        {

          "name": "Иван",

          "age": 30,

          "hobbies": ["reading", "programming"]

        }""";
    System.out.println(areJsonsEqual(firstJson, secondJson)); // Output: true
  }
}
