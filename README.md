# Lab5Android

# Лабораторная работа №5. UI Tests.

## Цели
Ознакомиться с принципами и получить практические навыки разработки UI тестов для Android приложений.

## Задачи
Предполагается, что все задачи решаются с помощью Instrumentation (Android) tests и Espresso Framework, если иное явно не указано в тексте задания.





# Ход работы
# Задача 1 Простейший UI тест
Ознакомьтесь с Espresso Framework: https://developer.android.com/training/testing/espresso. Разработайте приложение, в котором есть одна кнопка (Button) и одно текстовое поле (EditText). При (первом) нажатии на кнопку текст на кнопке должен меняться.
Напишите Espresso тест, который проверяет, что при повороте экрана содержимое текстового поля (каким бы оно ни было) сохраняется, а надпись на кнопке сбрасывается в исходное состояние.

Приведу код для понимания происходящего.

MainActivity.
```Java
public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    activityMainBinding.button.setText("Button was pressed");
                    count++;
                }
            }
        });
    }
}
```
activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/button"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/editTextTextPersonName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="textPersonName"
        android:text="@string/name"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/button"
        android:autofillHints="@string/name" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
После написания код я протестировал программу сам и убедился в выполненных требованиях. Сначала я нажал на кнопку и текст поменялся, потом дописал текст, а после перевернул экран и увидел, что текст кнопки вернуля в исходное состояние, а текст остался преждним.

Теперь проделаем такие же действия, но с помощью фреймворка.
Сразу преведу код теста.
MainActivityTest.
```Java
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testButton() {
        onView(withId(R.id.button)).check(matches(withText("Button")));
        onView(withId(R.id.editTextTextPersonName)).check(matches(withText("Name")));
        onView(withId(R.id.editTextTextPersonName)).perform(clearText());
        onView(withId(R.id.editTextTextPersonName)).perform(typeText("Nikita"));
        onView(withId(R.id.editTextTextPersonName)).check(matches(withText("Nikita")));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.button)).check(matches(withText("Button was pressed")));
        mainActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        });
        onView(withId(R.id.button)).check(matches(withText("Button")));
        onView(withId(R.id.editTextTextPersonName)).check(matches(withText("Nikita")));
    }
}
```
Из кода можно увидеть, что было сделано почти тоже самое только написано на фреймворке с возможность проверки корректности работы программы. Теперь я кратко расскажу, что я делал в это коде.
1. Проверил текст кнопки
2. Проверил текст поля
3. Убрал изначальный текст в поле
4. Написал свой текст(Nikita)
5. Убеддился,что этот текст действительно был написан
6. Нажал на кнопку
7. Проверил новый текст кнопки
8. Воспользовался предложенным кодом для переворота экранна
9. Проверил, что кнопка действительно сброилась в изначальное состояние
10. Проверил, что текст поля остался преждним

# Задача 2 Тестирование навигации.
Возьмите приложение из Лаб №3 о навигации (любое из решений). Напишите UI тесты, проверяющие навигацию между 4мя исходными Activity/Fragment (1-2-3-About). В отчете опишите, что проверяет каждый тест.
 

Сразу преведу код тестов.
```Java
public class MainActivity1Test {

    @Rule
    public ActivityScenarioRule<MainActivity1> mainActivityRule = new ActivityScenarioRule<>(MainActivity1.class);

    @Test
    public void checkButtonsInFirstActivity() {
        onView(withId(R.id.to_second)).check(matches(isDisplayed()));
        onView(withId(R.id.for_task_4)).check(matches(isDisplayed()));
        onView(withId(R.id.to_first)).check((doesNotExist()));
        onView(withId(R.id.to_third)).check((doesNotExist()));
        checkNavigationDrawer();
    }

    @Test
    public void checkButtonsInSecondActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check(matches(isDisplayed()));
        onView(withId(R.id.to_second)).check((doesNotExist()));
        onView(withId(R.id.for_task_4)).check((doesNotExist()));
        checkNavigationDrawer();
    }

    @Test
    public void checkButtonsInThirdActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_third)).perform(click());
        onView(withId(R.id.to_second)).check(matches(isDisplayed()));
        onView(withId(R.id.for_task_4)).check((doesNotExist()));
        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check((doesNotExist()));
        checkNavigationDrawer();
    }


    public void checkNavigationDrawer() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.drawer_layout)).check(matches(isOpen(Gravity.LEFT))).perform(DrawerActions.close());
    }

    @Test
    public void checkButtonBackInSecondActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(pressBack());
        checkButtonsInFirstActivity();
    }

    @Test
    public void checkButtonBackInThirdActivity() {
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_third)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(pressBack());

        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check(matches(isDisplayed()));
        onView(withId(R.id.to_second)).check((doesNotExist()));
        onView(withId(R.id.for_task_4)).check((doesNotExist()));

        onView(withId(R.id.drawer_layout)).perform(pressBack());
        checkButtonsInFirstActivity();

    }

    @Test
    public void checkScreenRotationForFirstActivity() {
        checkButtonsInFirstActivity();
        mainActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity1>() {
            @Override
            public void perform(MainActivity1 activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        });
        checkButtonsInFirstActivity();
    }

    @Test
    public void checkScreenRotationForSecondActivity() {
        checkButtonsInFirstActivity();
        mainActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity1>() {
            @Override
            public void perform(MainActivity1 activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        });
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_third)).check(matches(isDisplayed()));

    }


    @Test
    public void checkScreenRotationForThirdActivity() {
        checkButtonsInFirstActivity();
        mainActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity1>() {
            @Override
            public void perform(MainActivity1 activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        });
        onView(withId(R.id.to_second)).perform(click());
        onView(withId(R.id.to_third)).perform(click());
        onView(withId(R.id.to_first)).check(matches(isDisplayed()));
        onView(withId(R.id.to_second)).check(matches(isDisplayed()));
    }
}
```

Для выполнения задания яразделил тесты на части и проверя их отдельными методами.
Методы "checkButtonsIn..." проверяют наличие нужных кнопок на экране. Для первого активити идет простая проверка кнопока, а для следующих таже проверка, но с переходом в соответствующее активити.
Метод "checkNavigationDrawer" проверяет открыте и закрытие шторки.
Методы "checkButtonBack..." проверяют правильность перехода назад.
Методы "checkScreenRotation..." проверяют правильность переворота экрана.

# Выводы
В данной лабараторной работе использовался фрейворк "Espresso" который позволял нам проверить правильность работы нашей программы. На мой взгляд этот фреймворк оказался довольно удобным и полезным, я бы его стал использовать, но при использовании его впервые тестирование вышло довольно долгим из-за отсутствия понимания этого фреймворка. На данный момент эта лабраторная работа показалась мне самой интерестной. На все выполнение работы ушло 3-4 часа(с написанием отчета).Больше всего времени заняло выяснение проблемы с тестами имеющими переворот экрана.
