package llm;

import web_client.WebClient;

public class LLM extends WebClient implements ILLM {


}

interface ILLM {// 이것을 한국어와 한글, 몇몇 고유명사의 경우 영어만 사용하여 번역하고 쓸데없는 다른 표현은 모두 생략해
    enum LLMPlatform {
        GOOGLE, GROQ, TOGETHER_AI
    }
    enum LLMModel {
        GEMINI_2_0_FLASH(
                LLMPlatform.GOOGLE,
                "gemini-2.0-flash",
                "generateContent"),
        MIXTRAL_8x7b_32768(
                LLMPlatform.GROQ,
                "mixtral-8x7b-32768"),
        STABLE_DIFFUSION_XL_BASE_1_0(
                LLMPlatform.TOGETHER_AI,
                "stabilityai/stable-diffusion-xl-base-1.0");

        final private LLMPlatform platform;
        final private String name;
        final private String[] functions;

        LLMModel(LLMPlatform platform, String name, String ...functions) {
            this.platform = platform;
            this.name = name;
            this.functions = functions;
        }


        public String getName() {
            return name;
        }

        public String[] getFunctions() {
            return functions;
        }
    }
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