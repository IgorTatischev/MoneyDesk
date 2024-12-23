package com.money.desk.authorization.presentation.screens.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.money.desk.authorization.presentation.screens.onboarding.OnboardingPages
import com.money.ui.theme.MoneyDeskTheme

@Composable
internal fun OnboardingScreenPageContent(modifier: Modifier = Modifier, page: OnboardingPages) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(fraction = 0.95f),
            painter = painterResource(id = page.image),
            contentDescription = null,
        )
        Spacer(Modifier.height(40.dp))
        Text(
            text = stringResource(id = page.titleText),
            style = MaterialTheme.typography.displayMedium.copy(
                fontSize = 20.sp,
                lineHeight = 26.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = page.descriptionText),
            minLines = 3,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                lineHeight = 21.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview
private fun PageContentPreview() {
    MoneyDeskTheme {
        Surface {
            OnboardingScreenPageContent(
                Modifier,
                OnboardingPages.WALLET
            )
        }
    }
}