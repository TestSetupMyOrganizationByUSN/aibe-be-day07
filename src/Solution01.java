import llm.*;
import web_client.WebClient;

public class Solution01 {
    public static void main(String[] args) {

        LLM llm = new LLM();

        String prompt = System.getenv("GEMINI_PROMPT");
        String result = llm.callAPI(LLM.LLMModel.GEMINI_2_0_FLASH, """
                {
                  "contents": [{
                    "parts":[{"text": "%s"}]
                   }]
               }
               \s""".formatted(System.getenv("GEMINI_PROMPT")));


        String searchText = "\"text\": \"";
        int startIndex = result.indexOf(searchText) + searchText.length();
        int endIndex = result.indexOf("\"", startIndex);

        if (startIndex != -1 && endIndex != -1) {
            String text = result.substring(startIndex, endIndex);
            System.out.println(text);
        } else {
            System.out.println("Text not found.");
        }




        Slack slack = new Slack();

    }
}


class Slack extends WebClient {

}



