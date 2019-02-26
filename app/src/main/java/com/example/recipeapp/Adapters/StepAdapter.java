package com.example.recipeapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import com.example.recipeapp.Step1;
import com.example.recipeapp.Step2;
import com.example.recipeapp.Step3;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * This class is the Step Adapter class through which 3 different fragments would be created namely:
 *      1. Step 1() Fragment
 *      2. Step 2() Fragment
 *      3. Step 3() Fragment
 *
 * This fragments would be called through StepperLayout inside fragment_create_your_own.xml file.
 *
 * @author utsav
 * @date 22-Feb-2019
 */
public class StepAdapter extends AbstractFragmentStepAdapter
{
    //Constructor which takes FragmentManager and Context as parameter
    public StepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    /***
     * This method takes in the position.
     * The initial position is 0, which would return the Step 1 Fragment.
     * @param position
     * @return Step
     */
    @Override
    public Step createStep(int position)
    {
        Bundle b = new Bundle();

        switch (position)
        {
            case 0:
                Step1 step1Fragment = new Step1();
                step1Fragment.setArguments(b);
                return step1Fragment;
            case 1:
                Step2 step2Fragment = new Step2();
                step2Fragment.setArguments(b);
                return step2Fragment;
            case 2:
                Step3 step3Fragment = new Step3();
                step3Fragment.setArguments(b);
                return step3Fragment;
        }

        return null;
    }

    /***
     * This returns the number of Fragments inside StepperLayout
     * @return int
     */
    @Override
    public int getCount() {
        return 3;
    }

    /***
     * This method is used to define labels of Button when going to next Step.
     *
     * By default, the labels are Next and Back.
     * @param position
     * @return StepViewModel
     */
    @NonNull
    @Override
    public StepViewModel getViewModel(int position)
    {
        StepViewModel.Builder builder = new StepViewModel.Builder(context);

        switch (position)
        {
            case 0:
                builder.setEndButtonLabel("Ingredients");
                break;
            case 1:
                builder.setBackButtonLabel("Information");
                builder.setEndButtonLabel("Directions");
                break;
            case 2:
                builder.setBackButtonLabel("Ingredients");
                builder.setEndButtonLabel("");
                break;
        }

        return builder.create();
    }
}
