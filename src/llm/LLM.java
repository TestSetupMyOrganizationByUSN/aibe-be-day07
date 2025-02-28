package llm;

import web_client.WebClient;

import java.io.IOException;

public class LLM extends WebClient implements ILLM {


    public LLM() {
        super();
    }
    @Override
    public String callAPI(LLMModel model, String body) {
        String path = "";
        HttpMethod method = HttpMethod.GET;
        String[] headers = null;
        String apiKey = "";

        switch (model.platform) {
            case GOOGLE -> {
                path = "https://generativelanguage.googleapis.com/v1beta/models/%s:%s?key=%s"
                        .formatted(model.name, model.action.name, model.platform.apiKey);
                method = HttpMethod.POST;
                headers = new String[] {"Content-Type", "application/json"};
            }
            case GROQ -> {
            }
            case TOGETHER_AI -> {
            }
        }


        try {
            return sendRequest(makeRequest(path, method, body, headers));
        } catch (IOException | InterruptedException e) {
            logger.severe(e.toString());
            throw new RuntimeException(e);
        }
    }
}

interface ILLM {
    enum LLMPlatform {
        GOOGLE(System.getenv("GEMINI_API_KEY")),
        GROQ(System.getenv("GROQ_API_KEY")),
        TOGETHER_AI(System.getenv("TOGETHER_AI_API_KEY"));

        final String apiKey;
        LLMPlatform(String apiKey) {
            this.apiKey = apiKey;
        }
    }
    enum LLMModel {
        GEMINI_2_0_FLASH(
                LLMPlatform.GOOGLE,
                "gemini-2.0-flash",
                LLMAction.GENERATE_CONTENT),
        MIXTRAL_8x7b_32768(
                LLMPlatform.GROQ,
                "mixtral-8x7b-32768",
                null),
        STABLE_DIFFUSION_XL_BASE_1_0(
                LLMPlatform.TOGETHER_AI,
                "stabilityai/stable-diffusion-xl-base-1.0",
                null);

        final public LLMPlatform platform;
        final public String name;
        final public LLMAction action;

        LLMModel(LLMPlatform platform, String name, LLMAction action) {
            this.platform = platform;
            this.name = name;
            this.action = action;
        }

        enum LLMAction {
            GENERATE_CONTENT("generateContent");
            final String name;

            LLMAction(String name) {
                this.name = name;
            }
        }

    }
    String callAPI(LLMModel model, String body);
}
// https://console.groq.com/playground
// https://console.groq.com/docs/models
// https://console.groq.com/docs/rate-limits
// -> llama-3.3-70b-versatile (패러미터, 입/출력)
// - 대안1 : llama-3.1-8b-instant (패러미터, 입/출력 2위)
// - 대안2 : mixtral-8x7b-32768 (JSON Structure)
// - 대안3 : llama-guard-3-8b (protect)
// -> https://console.groq.com/docs/text-chat
// + Mixtral performs best at generating JSON, followed by Gemma, then Llama
// 미안합니다 - 쓰레드와 람다(하기 싫어도 Spring Security 때문에...)는 월요일날 합시다 (뭐시 중한데...)
// -> 최종결론 : https://aistudio.google.com/prompts/new_chat
// - gemini를 쓰는 걸로... -> 실은 하나로 하고 싶어 -> openai api를 쓰면 됀다
// https://api.together.xyz/playground
// https://api.together.xyz/models
// -> stabilityai/stable-diffusion-xl-base-1.0
// - 대안 : black-forest-labs/FLUX.1-schnell-Free