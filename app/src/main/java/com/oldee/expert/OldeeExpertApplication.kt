package com.oldee.expert

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class OldeeExpertApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }
}

/*

<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <androidx.drawerlayout.widget.DrawerLayout
        tools:context=".ui.MainActivity"
        android:id="@+id/main_drawer"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight="1"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent">

                <RelativeLayout
                    android:id="@+id/rl_progress"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:visibility="gone">

                    <com.google.android.material.progressindicator.CircularProgressIndicator
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_centerHorizontal="true"
                        android:layout_centerVertical="true"
                        android:indeterminate="true" />
                </RelativeLayout>

                <androidx.fragment.app.FragmentContainerView
                    android:id="@+id/nav_host_fragment"
                    android:name="androidx.navigation.fragment.NavHostFragment"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    app:defaultNavHost="true"
                    app:navGraph="@navigation/nav_graph" />
            </RelativeLayout>

            <ProgressBar
                android:visibility="gone"
                android:id="@+id/pb"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintBottom_toBottomOf="parent"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
        </androidx.constraintlayout.widget.ConstraintLayout>

        <include android:id="@+id/menu_drawer"
            layout="@layout/layout_home_right_slide_menu"/>
    </androidx.drawerlayout.widget.DrawerLayout>

    <!--    </androidx.drawerlayout.widget.DrawerLayout>-->
</layout>

 */