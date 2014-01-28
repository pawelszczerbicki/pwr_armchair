package pl.cywek.chair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ChairActivity extends Activity {

	private enum SeatElement {
		ZAGLOWEK, LEDZWIE, CALOSC, OPARCIE, SIEDZENIE, NONE
	}

	private SeatElement mSeatElement;

	private RelativeLayout mSeat;
	private SeekBar mFirstSeekBar;
	private SeekBar mSecondSeekBar;
	private SeekBar mThirdSeekBar;

	private ImageView mPlus1;
	private ImageView mPlus2;
	private ImageView mPlus3;

	private Button mBtnCalibrate;

	private ImageView mMinus1;
	private ImageView mMinus2;
	private ImageView mMinus3;

	private TextView mTxtTitle;

	private TextView mTxtFirstSlider;

	private TextView mTxtSecondSlider;
	private TextView mTxtThirdSlider;

	Chair mChair = Chair.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chair);

		mSeat = (RelativeLayout) findViewById(R.id.fotel);
		mFirstSeekBar = (SeekBar) findViewById(R.id.seekBarFS);
		mSecondSeekBar = (SeekBar) findViewById(R.id.seekBarSS);
		mThirdSeekBar = (SeekBar) findViewById(R.id.seekBarTS);

		mTxtFirstSlider = (TextView) findViewById(R.id.TextViewFS);
		mTxtSecondSlider = (TextView) findViewById(R.id.TextViewSS);
		mTxtThirdSlider = (TextView) findViewById(R.id.TextViewTS);

		mBtnCalibrate = (Button) findViewById(R.id.btnCalibrate);

		mTxtTitle = (TextView) findViewById(R.id.TextViewTitle);

		mPlus1 = (ImageView) findViewById(R.id.plus1);
		mPlus2 = (ImageView) findViewById(R.id.plus2);
		mPlus3 = (ImageView) findViewById(R.id.plus3);

		mMinus1 = (ImageView) findViewById(R.id.minus1);
		mMinus2 = (ImageView) findViewById(R.id.minus2);
		mMinus3 = (ImageView) findViewById(R.id.minus3);

		mFirstSeekBar.setVisibility(View.GONE);
		mPlus1.setVisibility(View.GONE);
		mMinus1.setVisibility(View.GONE);

		sectionSec(View.GONE);
		sectionThird(View.GONE);

		mSeatElement = SeatElement.NONE;

		// img_btn_chair - kontroluje caly fotel

		ImageButton img_btn_chair = (ImageButton) findViewById(R.id.btn_calosc);
		img_btn_chair.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activateChair();

			}
		});

		// img_btn_bolster - kontroluje sam zaglowek

		ImageButton img_btn_bolster = (ImageButton) findViewById(R.id.btn_zaglowek);
		img_btn_bolster.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activateBolster();

			}
		});

		// img_btn_back - kontroluje cale oparcie

		ImageButton img_btn_back = (ImageButton) findViewById(R.id.btn_oparcie);
		img_btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activateBack();

			}
		});

		// img_btn_loins - kontroluje ledzwie (element oparcia)

		ImageButton img_btn_loins = (ImageButton) findViewById(R.id.btn_ledzwie);
		img_btn_loins.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activateLoinsElement();

			}
		});

		// img_btn_bottom_seat - kontroluje siedzisko)

		ImageButton img_btn_bottom_seat = (ImageButton) findViewById(R.id.btn_siedzisko);
		img_btn_bottom_seat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activateBottomSeat();

			}
		});

		new AtmosphereInitTask(this).execute();

		mBtnCalibrate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AtmosphereTask().execute("", "CALIBRATE", "");
			}
		});

	}

	
	public void updateValues() {
		switch (mSeatElement) {
		case OPARCIE:
			mFirstSeekBar.setProgress(mChair.getmModel().getmPO());
			break;
		case ZAGLOWEK:
			mFirstSeekBar.setProgress(mChair.getmModel().getmZG());
			mSecondSeekBar.setProgress(mChair.getmModel().getmPZ());
			break;
		case LEDZWIE:
			mFirstSeekBar.setProgress(mChair.getmModel().getmGW());
			mSecondSeekBar.setProgress(mChair.getmModel().getmGG());
			break;
		case CALOSC:
			mFirstSeekBar.setProgress(mChair.getmModel().getmSP());
			break;
		case SIEDZENIE:
			mFirstSeekBar.setProgress(mChair.getmModel().getmWF());
			mSecondSeekBar.setProgress(mChair.getmModel().getmUG());
			mThirdSeekBar.setProgress(mChair.getmModel().getmPF());
			break;
		case NONE:
			// do nothing

		}
	}

	ProgressDialog dialog;

	public void dialogStart(final String title, final String desc) {
runOnUiThread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(ChairActivity.this,
				title,
				desc, true);	
	}
});
		Log.d("chair", "Dialog start");
	

	}

	public void dialogStop() {
		Log.d("chair", "Dialog stop");

		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dialog.dismiss();	
			}
		});

	}

	public void enableUI(boolean enabled) {
		mFirstSeekBar.setEnabled(enabled);
		mSecondSeekBar.setEnabled(enabled);
		mThirdSeekBar.setEnabled(enabled);
		findViewById(R.id.btn_calosc).setEnabled(enabled);
		findViewById(R.id.btn_ledzwie).setEnabled(enabled);
		findViewById(R.id.btn_oparcie).setEnabled(enabled);
		findViewById(R.id.btn_zaglowek).setEnabled(enabled);
		findViewById(R.id.btn_siedzisko).setEnabled(enabled);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds a items to the action bar if it is
		// present.
		getMenuInflater().inflate(R.menu.chair, menu);

		return true;
	}

	public void activateBolster() {
		Log.i("chair", "activateBolster");
		// ZG - wysuniecie
		// PZ - pochylenie,

		mSeatElement = SeatElement.ZAGLOWEK;

		mSeat.setBackgroundResource(R.drawable.zaglowek);

		mTxtTitle.setText(this.getString(R.string.head));
		mTxtFirstSlider.setText(this.getString(R.string.eject));
		mTxtSecondSlider.setText(this.getString(R.string.tilt));

		mFirstSeekBar.setProgress(mChair.getmModel().getmZG());
		mSecondSeekBar.setProgress(mChair.getmModel().getmPZ());

		mFirstSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mChair.zg("" + seekBar.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

		mSecondSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						mChair.pz("" + seekBar.getProgress());
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
					}
				});

		sectionFirst(View.VISIBLE);
		sectionSec(View.VISIBLE);
		sectionThird(View.GONE);
	}

	public void activateBottomSeat() {
		// WF – wydłużenie siedziska fotela przód/tył,
		// UG – pochylenie tylnej części siedziska,
		// PF – pochylenie przedniej części fotela góra/dół,

		mSeat.setBackgroundResource(R.drawable.siedzenie);
		mSeatElement = SeatElement.SIEDZENIE;

		mFirstSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mChair.wf("" + seekBar.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

		mSecondSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						mChair.ug("" + seekBar.getProgress());
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
					}
				});

		mThirdSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mChair.pf("" + seekBar.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

		mTxtTitle.setText(this.getString(R.string.bottom));
		mTxtFirstSlider.setText(this.getString(R.string.lengthen));
		mTxtSecondSlider.setText(this.getString(R.string.tilt_bottom));
		mTxtThirdSlider.setText(this.getString(R.string.tilt_front));

		mFirstSeekBar.setProgress(mChair.getmModel().getmWF());
		mSecondSeekBar.setProgress(mChair.getmModel().getmUG());
		mThirdSeekBar.setProgress(mChair.getmModel().getmPF());

		
		sectionFirst(View.VISIBLE);
		sectionSec(View.VISIBLE);
		sectionThird(View.VISIBLE);
	}

	public void activateLoinsElement() {
		// GG – przesunięcie wybrzuszenia oparcia pod kręgosłupem w
		// położeniu
		// góra/dół ,
		// GW – wybrzuszenie oparcia pod kręgosłupem,

		mSeat.setBackgroundResource(R.drawable.ledzwie);
		mSeatElement = SeatElement.LEDZWIE;

		mTxtTitle.setText(this.getString(R.string.loins));
		mTxtFirstSlider.setText(this.getString(R.string.bulge));
		mTxtSecondSlider.setText(this.getString(R.string.height));
		

		mFirstSeekBar.setProgress(mChair.getmModel().getmGW());
		mSecondSeekBar.setProgress(mChair.getmModel().getmGG());

		mFirstSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mChair.gw("" + seekBar.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

		mSecondSeekBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						mChair.gg("" + seekBar.getProgress());
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

					}
				});

		sectionFirst(View.VISIBLE);
		sectionSec(View.VISIBLE);
		sectionThird(View.GONE);
	}

	public void activateChair() {
		// SP – przesunięcie całego fotela przód/tył,

		mTxtTitle.setText(this.getString(R.string.all));
		mSeat.setBackgroundResource(R.drawable.calosc);
		mSeatElement = SeatElement.CALOSC;
		

		mFirstSeekBar.setProgress(mChair.getmModel().getmSP());

		mTxtFirstSlider.setText(this.getString(R.string.move));

		mFirstSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mChair.sp("" + seekBar.getProgress());

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

		sectionFirst(View.VISIBLE);
		sectionSec(View.GONE);
		sectionThird(View.GONE);
	}

	public void activateBack() {
		// PO – pochylenie oparcia fotela,

		mSeat.setBackgroundResource(R.drawable.oparcie);
		mSeatElement = SeatElement.OPARCIE;

		mTxtTitle.setText(this.getString(R.string.back));
		mTxtFirstSlider.setText(this.getString(R.string.tilt));
		
		mFirstSeekBar.setProgress(mChair.getmModel().getmPO());

		mFirstSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mChair.po("" + seekBar.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

		sectionFirst(View.VISIBLE);
		sectionSec(View.GONE);
		sectionThird(View.GONE);

	}

	private void sectionFirst(int visibility) {
		mTxtFirstSlider.setVisibility(visibility);
		mFirstSeekBar.setVisibility(visibility);
		mMinus1.setVisibility(visibility);
		mPlus1.setVisibility(visibility);
	}

	private void sectionSec(int visibility) {
		mTxtSecondSlider.setVisibility(visibility);
		mSecondSeekBar.setVisibility(visibility);
		mMinus2.setVisibility(visibility);
		mPlus2.setVisibility(visibility);
	}

	private void sectionThird(int visibility) {
		mTxtThirdSlider.setVisibility(visibility);
		mThirdSeekBar.setVisibility(visibility);
		mMinus3.setVisibility(visibility);
		mPlus3.setVisibility(visibility);
	}

}
