package com.mition.realty;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mition.realty.contract.ContractRequestActivity;
import com.mition.realty.contract.ContractStateActivity;
import com.mition.realty.contract.PDFViewerActivity;
import com.mition.realty.util.model.Transaction;
import com.mition.realty.util.model.User;
import com.mition.realty.util.singleton.SingletonNetwork;
import com.mition.realty.util.singleton.SingletonUser;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    ImageView iv_nav_contract_request;
    ImageView iv_nav_contract_state;
    CoordinatorLayout coordinatorlayout;

    LinearLayout ll_trasaction_container_for_recipient, ll_trasaction_container_for_sender;
    TextView tv_pdf, tv_accept;
    ImageView iv_logout;
    ImageView iv_new_badge;

    TextView tv_message, tv_accept_description;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = SingletonUser.getInstance().getUser();

        coordinatorlayout = findViewById(R.id.coordinatorlayout);
        iv_new_badge = findViewById(R.id.iv_new_badge);

        ll_trasaction_container_for_sender = findViewById(R.id.ll_trasaction_container_for_sender);
        ll_trasaction_container_for_recipient = findViewById(R.id.ll_trasaction_container_for_recipient);
        tv_accept = findViewById(R.id.tv_accept);
        tv_pdf = findViewById(R.id.tv_pdf);
        iv_logout = findViewById(R.id.iv_logout);

        tv_message = findViewById(R.id.tv_message);
        tv_accept_description = findViewById(R.id.tv_accept_description);

        iv_nav_contract_request = findViewById(R.id.iv_nav_contract_request);
        iv_nav_contract_state = findViewById(R.id.iv_nav_contract_state);
        iv_nav_contract_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContractRequestActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        iv_nav_contract_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContractStateActivity.class);
                startActivity(intent);
                iv_new_badge.setVisibility(View.GONE);
            }
        });

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("USER_ID", null);
                                editor.commit();

                                Intent i = new Intent(MainActivity.this, LandingActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();
            }
        });

        if (user.is_checked_registered_contract) {
            iv_new_badge.setVisibility(View.GONE);
        } else {
            iv_new_badge.setVisibility(View.VISIBLE);
        }
        refresh();
    }

    public void refresh() {
        if (user.transaction_id != null && !user.transaction_id.equals("")) {
            if (user.type_of_party.equals("sender")) {
                connectionGetSenderTransaction(user.transaction_id);
            } else if (user.type_of_party.equals("recipient")) {
                connectionGetRecipientTransaction(user.transaction_id);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 120) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorlayout, "계약서 전송이 완료되었습니다.", Snackbar.LENGTH_LONG);
            snackbar.show();
            connectionGetUser(user._id);
        }
    }

    public void connectionGetUser(String id) {
        SingletonNetwork.getInstance().getConnctionUser().getUser(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(User user) {
                        SingletonUser.getInstance().setUser(user);
                        if (user.transaction_id != null && !user.transaction_id.equals("")) {
                            if (user.type_of_party.equals("sender")) {
                                connectionGetSenderTransaction(user.transaction_id);
                            } else if (user.type_of_party.equals("recipient")) {
                                connectionGetRecipientTransaction(user.transaction_id);
                            }
                        }
                    }
                });
    }

    public void connectionGetSenderTransaction(String id) {
        SingletonNetwork.getInstance().getConnectionTransaction().getSenderTransaction(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Transaction>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Transaction transaction) {
                        if (transaction != null && !transaction._id.equals("")) {
                            UIThreadForSender();
                        }
                    }
                });
    }

    public void connectionGetRecipientTransaction(String id) {
        SingletonNetwork.getInstance().getConnectionTransaction().getRecipientTransaction(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Transaction>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Transaction transaction) {
                        if (transaction != null && !transaction._id.equals("")) {
                            UIThreadForRecipient(transaction);
                        }
                    }
                });
    }

    public void connectionAcceptTransaction(String id) {
        SingletonNetwork.getInstance().getConnectionTransaction().acceptTransaction(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Transaction>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Transaction transaction) {
                        iv_new_badge.setVisibility(View.VISIBLE);
                        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("IS_CHECKED_REGISTERED_CONTRACT", false);
                        editor.commit();
                    }
                });
    }

    public void UIThreadForSender() {
        ll_trasaction_container_for_sender.setVisibility(View.VISIBLE);
        ll_trasaction_container_for_recipient.setVisibility(View.GONE);
    }

    public void UIThreadForRecipient(final Transaction transaction) {
        ll_trasaction_container_for_sender.setVisibility(View.GONE);
        ll_trasaction_container_for_recipient.setVisibility(View.VISIBLE);

        tv_message.setText(transaction.sender_name + "님으로부터 계약서가 도착하였습니다.");
        tv_accept_description.setText(transaction.sender_name + "님이 요청한 계약을 수락하시겠습니까?");

        tv_pdf.setText(fromHtml("<u>" + transaction.file_name + "</u>"));
        tv_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PDFViewerActivity.class);
                intent.putExtra("PDF_FILE_PATH", transaction.path);
                startActivity(intent);
            }
        });


        tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_trasaction_container_for_sender.setVisibility(View.GONE);
                ll_trasaction_container_for_recipient.setVisibility(View.GONE);

                connectionAcceptTransaction(transaction._id);
            }
        });
    }

    // Html.fromHtml deprecated in Android N
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
