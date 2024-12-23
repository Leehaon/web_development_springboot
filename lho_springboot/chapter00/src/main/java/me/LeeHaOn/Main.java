package me.LeeHaOn;
/*
    스프링 부트 3 프로젝트 만들기

        01 단계 - 현재까지 여러분들이 프로젝트를 만든 것은 스프링 부트 3 프로젝트가 아니라
            '그레이들 프로젝트'를 생성한 예시입니다.
            해당 그레이들 프로젝트를 스프링 부트 3 프로젝트로 만들 예정입니다.

            그레이들 설정 파일로 이동 합니다. -> build.gradle
 */
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}